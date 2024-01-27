package com.hmall.common.config;

import com.hmall.common.interceptor.UserInfoInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: dy
 * @Date: 2024/1/27 22:49
 * @Description:
 */
@Configuration  //  1. 添加注解
@ConditionalOnClass(DispatcherServlet.class)
public class MvcConfig implements WebMvcConfigurer {    //  2. 实现 WebMvcConfigurer 接口


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       //  3. 添加拦截器
        registry.addInterceptor(new UserInfoInterceptor());
    }
}
