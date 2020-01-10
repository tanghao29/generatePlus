package com.hy.ssm.lanxin.test;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CacheFilter implements Filter, ApplicationContextAware {
    private static final Logger log= LoggerFactory.getLogger(CacheFilter.class);
    private static ApplicationContext ctx;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx=applicationContext;
    }

    private String getHtmlFromCache(){
        StringRedisTemplate stringRedisTemplate=(StringRedisTemplate) ctx.getBean("redisTemplate");
        return stringRedisTemplate.opsForValue().get("home");
    }

    private void putIntoCache(String html){
        StringRedisTemplate stringRedisTemplate=(StringRedisTemplate) ctx.getBean("redisTemplate");
        stringRedisTemplate.opsForValue().set("home",html, TimeUnit.MINUTES.toSeconds(10));

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        if(!request.getRequestURI().equals(request.getContextPath()+"/html/Account_Course.html")){
            filterChain.doFilter(request,response);
            return;
        }

        String html=getHtmlFromCache();
        if(null==html){
            ResponseWrapper responseWrapper=new ResponseWrapper(response);
            filterChain.doFilter(request,responseWrapper);
            html=responseWrapper.getResult();
            putIntoCache(html);
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(html);


    }
}
