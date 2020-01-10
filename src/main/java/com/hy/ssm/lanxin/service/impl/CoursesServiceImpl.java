package com.hy.ssm.lanxin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.ssm.lanxin.entity.Courses;
import com.hy.ssm.lanxin.mapper.CoursesMapper;
import com.hy.ssm.lanxin.service.ICoursesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements ICoursesService {

}
