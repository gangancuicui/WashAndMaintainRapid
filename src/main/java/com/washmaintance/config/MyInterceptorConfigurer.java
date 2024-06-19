package com.washmaintance.config;


import com.washmaintance.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyInterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor());
//        InterceptorRegistration registration=registry.addInterceptor(new MyInterceptor());
//        registration.excludePathPatterns("/**/*.html","/**/*.js","/**/*.css","/**/*.jpg","/**/*.png");
    }
}
