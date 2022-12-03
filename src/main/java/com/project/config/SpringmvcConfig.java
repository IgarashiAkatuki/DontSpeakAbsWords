package com.project.config;

import com.project.filter.CharacterFilter;
import com.project.filter.IpFilter;
import com.project.interceptor.UserInterceptor;
import com.project.listener.SessionListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.EventListener;

@SuppressWarnings("rawtypes")
@Configuration
public class SpringmvcConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean characterFilter(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CharacterFilter());
        bean.addUrlPatterns("/*");
        bean.setName("CharacterEncodingFiler");

        return bean;
    }

    @Bean
    public FilterRegistrationBean ipFilter(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new IpFilter());
        bean.addUrlPatterns("/*");
        bean.setName("IpFilter");

        return bean;
    }

//     用SpringSecurity 代替过滤器/拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration interceptor = registry.addInterceptor(new UserInterceptor());
//        interceptor.addPathPatterns("/admin/*");
////        设置不被拦截的请求
//        interceptor.excludePathPatterns("/admin/login");
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/test").setViewName("index");
        registry.addViewController("/admin/login").setViewName("admin/LoginTest");
//        registry.addViewController("/admin/backstage").setViewName("admin/backstage");

    }



    @Bean
    public ServletListenerRegistrationBean sessionListener(){
        ServletListenerRegistrationBean<EventListener> bean = new ServletListenerRegistrationBean<>();
        bean.setListener(new SessionListener());
        return bean;
    }
}
