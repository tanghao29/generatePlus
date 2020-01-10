package com.hy.ssm.lanxin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.ssm.lanxin.entity.Courses;
import com.hy.ssm.lanxin.uitls.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

@CacheNamespace(implementation = RedisCache.class)
public interface CoursesMapper extends BaseMapper<Courses> {


}
