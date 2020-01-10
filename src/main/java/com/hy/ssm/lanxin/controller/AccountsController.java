package com.hy.ssm.lanxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.ssm.lanxin.entity.Accounts;
import com.hy.ssm.lanxin.entity.Courses;
import com.hy.ssm.lanxin.service.IAccountsService;
import com.hy.ssm.lanxin.service.ICoursesService;
import com.hy.ssm.lanxin.uitls.AccountJson;
import com.hy.ssm.lanxin.uitls.Ajax;
import io.swagger.annotations.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author TangHao
 * @since 2019-12-05
 */

@Controller
@RequestMapping("/accounts")
@Api(value = "关于学生表(student)的接口")
public class AccountsController {
    @Autowired
    private IAccountsService accountsService;

    @Autowired
    private ICoursesService coursesService;

    //       分页查询
    @RequestMapping(value = "/queryAll.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(httpMethod = "get",value="查询所有并分页或根据条件查询所有并分页")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "查询成功！",response = AccountJson.class),
            @ApiResponse(code = 500,message = "查询失败！")
    })
    public AccountJson queryAll(@ApiParam(required = true,name = "page",value = "当前是第几页")@RequestParam(value = "page",defaultValue = "1") Integer page, @ApiParam(required=true,name = "limit" , value = "每页分几条") @RequestParam(value = "limit" ,defaultValue = "3")Integer limit, @ApiParam(required=false,name = "accounts" , value = "条件查询的条件封装的对象")  Accounts accounts, HttpSession session){
        IPage list1=accountsService.pages(page,limit,accounts,session);
        AccountJson accountJson=new AccountJson();
        accountJson.setCode(0);
        accountJson.setMsg("");
        accountJson.setCount(Integer.parseInt(String.valueOf(list1.getTotal())));
        accountJson.setData(list1.getRecords());
        return accountJson;
    }

//    添加
    @PostMapping("/Add.do")
    @ResponseBody
    @ApiOperation(httpMethod = "post",value="添加一个学生")
    @ApiResponses( value = {
            @ApiResponse( code = 200 , message = "添加成功！" , response = String.class) ,
            @ApiResponse( code = 500 , message = "内部出错！")
    } )
    public String Add(@ApiParam(required = true , name = "accounts" , value = "需要保存到数据的对象") Accounts accounts) {
        String num="0";
        try {
            accounts.setAid(UUID.randomUUID().toString());
            accountsService.Add(accounts);
        } catch (Exception e) {
            e.printStackTrace();
            num="1";
        }
        return num;
        }

    //    上传图片
    @PostMapping("/upload.do")
    @ResponseBody
    @ApiOperation( httpMethod = "post" , value = "上传图片到服务器" )
    @ApiResponses( value = {
            @ApiResponse( code = 200 , message = "上传成功！" , response = Ajax.class ) ,
            @ApiResponse( code = 500 , message = "内部出错！" )
    } )
    public Ajax upload(@ApiParam(required = true , name = "file" , value = "要上串的文件封装为MultipartFile对象" )@RequestParam("file")MultipartFile multipartFile, HttpServletRequest request, Ajax ajax) {
        int a=0;
        // 图片上传
// 设置图片名称，不能重复，可以使用uuid//意思为产生一个随机的名字
        String picName = UUID.randomUUID().toString();
// 获取文件名
        String oriName = multipartFile.getOriginalFilename();
// 获取图片(文件名)后缀
        String extName = oriName.substring(oriName.lastIndexOf("."));
        try {
// 开始上传(设置上传的路径和名称)
            multipartFile.transferTo(new File( request.getSession().getServletContext().getRealPath("/")+"\\img\\"+ picName + extName));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            a=1;
        }
        ajax.setFilename( picName + extName);
        ajax.setCode(a);

        return ajax;
    }

//    删除
    @RequestMapping(value = "/delete.do" ,method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation( httpMethod = "post" , value = "根据id删除一条数据" )
    @ApiResponses( value = {
            @ApiResponse( code = 200 , message = "删除数据！" , response = Integer.class ) ,
            @ApiResponse( code = 500 , message = "内部出错！" )
    } )
    public Integer delete(@ApiParam(required = true , name = "aid" , value = "要删除数据的id(主键)" ) @RequestParam("aid") String aid){
        QueryWrapper<Accounts> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
       int num=200;
        try {
            accountsService.remove(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            num=404;
        }
        return num;
    }

//    根据ID查询
    @RequestMapping(value = "/queryById.do" , method = RequestMethod.GET)
    @ApiOperation( httpMethod = "get" , value = "根据id(主键)查询数据" )
    @ApiResponses( value = {
            @ApiResponse(code = 200 , message = "根据id查寻成功！" , response = String.class )
    } )
    public String queryById(@ApiParam(required = true , name = "aid" , value = "根据id查询" )@RequestParam String aid, Model model){
        QueryWrapper<Accounts> wrapper=new QueryWrapper<>();
        wrapper.eq("aid",aid);
        Accounts accounts=accountsService.getOne(wrapper);
        model.addAttribute("accounts",accounts);
        return "updateAccount";
    }

//    提交修改
    @RequestMapping(value = "/updates.do" , method = RequestMethod.GET)
    @ResponseBody
    public String update(Accounts accounts){
        String num="0";
        try {
            accountsService.saveOrUpdate(accounts);
        } catch (Exception e) {
            num="1";
        }
        return num;

    }

//    同时删除多个
    @RequestMapping("/deDuoge.do")
    @ResponseBody
    public String deDuoge(String boxs){
      List<String> list= Arrays.asList(boxs);
        String num="0";
        try {
            accountsService.removeByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
            num="1";
        }
//        return "redirect:/accounts/queryAll.do";
        return num;
    }

//先到后台再到视图页面
    @RequestMapping("toAdd.do")
    public ModelAndView toAdd(ModelAndView modelAndView){
        modelAndView.setViewName("AccountAdd");
        return modelAndView;
    }

//先到后台再到视图页面
    @RequestMapping("inputExcel.do")
    @ResponseBody
    public Ajax  inputExcel(@RequestParam("file")MultipartFile multipartFile,Ajax ajax) throws IOException {
        List<Courses> list1=coursesService.list();
        int a=200;
        try {
            List<Accounts> list=accountsService.inputEccel(multipartFile.getInputStream(),list1);
            accountsService.saveBatch(list);
        } catch (IOException e) {
            e.printStackTrace();
            a=404;
        }
        ajax.setCode(a);
        return ajax;
    }

//    导出数据到excel文件中
    @RequestMapping("exportExcel.do")
    void exportExcel(HttpServletResponse response, Accounts account, HttpSession session) throws IOException {
        response.setContentType( "application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=1.xls");
        Workbook workbook=accountsService.exportExcel(account,session);
        workbook.write(response.getOutputStream());

    }

    @RequestMapping("/queryid.do")
    @ResponseBody
    public Accounts queryid(String id){
        return accountsService.queryid(id);
    }

    @RequestMapping("/updateid.do")
    @ResponseBody
    public Accounts updateid(Accounts accounts){
        return accountsService.     updateid(accounts);
    }

    @RequestMapping("/deleteid.do")
    @ResponseBody
    public String deleteid(String id){
         accountsService.deleteid(id);
         String str=id+"已删除！";
         return str;
    }




}
