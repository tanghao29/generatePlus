package com.hy.ssm.lanxin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.ssm.lanxin.entity.Accounts;
import com.hy.ssm.lanxin.entity.Courses;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TangHao
 * @since 2019-12-05
 */
public interface IAccountsService extends IService<Accounts> {

    public void Add(Accounts accounts);

    IPage<Accounts> pages(Integer page,Integer limit, Accounts accounts,HttpSession session);

    List<Accounts> inputEccel(InputStream inputStream, List<Courses> list1);

    Workbook exportExcel(Accounts account, HttpSession session);

    public Accounts queryid(String id);

    public void deleteid(String id);

    public Accounts updateid(Accounts accounts);





}
