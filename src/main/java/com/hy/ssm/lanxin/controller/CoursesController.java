package com.hy.ssm.lanxin.controller;

import com.hy.ssm.lanxin.entity.Courses;
import com.hy.ssm.lanxin.service.ICoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    private ICoursesService coursesService;

    @RequestMapping("/query_course_ajax.do")
    @ResponseBody
    public List query_course_ajax(){
        List<Courses> list=coursesService.list();
        return list;
    }


}
