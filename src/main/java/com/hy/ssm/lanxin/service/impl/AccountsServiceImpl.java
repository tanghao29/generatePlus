package com.hy.ssm.lanxin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.ssm.lanxin.entity.Accounts;
import com.hy.ssm.lanxin.entity.Courses;
import com.hy.ssm.lanxin.mapper.AccountsMapper;
import com.hy.ssm.lanxin.mapper.CoursesMapper;
import com.hy.ssm.lanxin.service.IAccountsService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TangHao
 * @since 2019-12-05
 */
@Service
@Transactional
@CacheConfig(cacheNames = "account")//把所有缓存放到这个集合里面或所有的都上着里面那key
@EnableCaching
public class AccountsServiceImpl extends ServiceImpl<AccountsMapper, Accounts> implements IAccountsService {

    @Autowired
    private AccountsMapper accountsMapper;

    @Autowired
    private CoursesMapper coursesMapper;

    @Override
    public void Add(Accounts accounts) {
        accountsMapper.Add(accounts);
    }

    public IPage<Accounts> pages(Integer page, Integer limit, Accounts accounts,HttpSession session) {
        //        分页
//        if (dqy == null) {
//            dqy = 1;
//        }
        Page<Accounts> accountsPage = new Page<>(page, limit);
        session.setAttribute("arraylist",accountsMapper.queryAlls(accounts));
        return accountsMapper.queryAll(accountsPage,accounts);
    }

    @Override
    public List<Accounts> inputEccel(InputStream inputStream, List<Courses> list1) {
        List<Accounts> list=new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
//            System.out.println(workbook.getNumberOfSheets()+"一共有多少个sheet");
            for(int a=0;a<workbook.getNumberOfSheets();a++) {//循环所有sheet页
                Sheet sheet = workbook.getSheetAt(a);
//                System.out.println(sheet.getLastRowNum()+"一共有多少行");
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {//循环所有行
                    Accounts accounts = new Accounts();

                    Row row = sheet.getRow(i);
//                    System.out.println(row.getLastCellNum()+"一共有多少列");
                    for (int j = 0; j < row.getLastCellNum(); j++) {//循环所有列
                        accounts.setAid(UUID.randomUUID().toString());//主键ID
                        String val="";
                        Cell cell = row.getCell(j);
                        System.out.println(cell.getCellTypeEnum()+"类型");
                        if(cell.getCellType()==Cell.CELL_TYPE_STRING){
                            val=cell.getStringCellValue();
                        }else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
                            val=String.valueOf(cell.getNumericCellValue()).substring(0,String.valueOf(cell.getNumericCellValue()).lastIndexOf("."));
                        }else{
                            System.out.println("没有此类型！");
                        }

                        if(j+1==1){//用户名
                            accounts.setAname(val);
                        }else if(j+1==2){//密码
                            accounts.setApass(val);
                        }else if(j+1==3){//性别
                            if(val=="男"){
                                accounts.setAsex(1);
                            }else{
                                accounts.setAsex(2);
                            }
                        }else if(j+1==4){//年龄
                            accounts.setAge(Integer.valueOf(val));
                        }else{//班级
                            for (int k = 0; k <list1.size() ; k++) {
                                if(list1.get(k).getCname().equals(val)){
                                    accounts.setCid(String.valueOf(list1.get(k).getCid()));
                                }
                            }
                        }

                        }
                    list.add(accounts);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Workbook exportExcel(Accounts account,HttpSession session) {
        Workbook workbook=new HSSFWorkbook();
        Sheet sheet=workbook.createSheet("学生基本信息");
        Row row=sheet.createRow(0);
        Cell cell1=row.createCell(0);
        cell1.setCellValue("用户名");
        Cell cell2=row.createCell(1);
        cell2.setCellValue("密码");
        Cell cell3=row.createCell(2);
        cell3.setCellValue("性别");
        Cell cell4=row.createCell(3);
        cell4.setCellValue("年龄");
        Cell cell5=row.createCell(4);
        cell5.setCellValue("班级");
        List<Accounts> accounts=(List<Accounts>)session.getAttribute("arraylist");
        for (int i = 0; i <accounts.size() ; i++) {
            Row row1=sheet.createRow(i+1);
            Cell cell6=row1.createCell(0);
            cell6.setCellValue(accounts.get(i).getAname());
            Cell cell7=row1.createCell(1);
            cell7.setCellValue(accounts.get(i).getApass());
            Cell cell8=row1.createCell(2);
            if(accounts.get(i).getAsex()==1){
                cell8.setCellValue("男");
            }else{
                cell8.setCellValue("女");
            }
            Cell cell9=row1.createCell(3);
            cell9.setCellValue(accounts.get(i).getAge());
            Cell cell10=row1.createCell(4);
            cell10.setCellValue(accounts.get(i).getCourses().getCname());

        }
        return workbook;
    }

    //查询一个新的时添加到缓存中
    @Cacheable(key = "'account_'+#id")//缓存的名字
    @Override
    public Accounts queryid(String id){
        return accountsMapper.selectById(id);
    }

    //删除时也删除缓存
    @CacheEvict(key = "'account_'+#id")
    @Override
    public void deleteid(String id){
        accountsMapper.deleteById(id);
    }

    @CachePut(key = " 'account_' + #accounts.aid ")
    @Override
    public Accounts updateid(Accounts accounts){
        accountsMapper.updateById(accounts);
        return accountsMapper.selectById(accounts.getAid());
    }


}
