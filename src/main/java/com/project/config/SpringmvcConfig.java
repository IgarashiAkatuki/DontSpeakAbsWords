package com.project.config;

import com.project.filter.CharacterFilter;
import com.project.filter.IpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;


@SuppressWarnings("rawtypes")
@Configuration
public class SpringmvcConfig implements WebMvcConfigurer {

    // 设置字符过滤器，防止乱码
    @Bean
    public FilterRegistrationBean characterFilter(){

        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CharacterFilter());
        bean.addUrlPatterns("/*");
        bean.setName("CharacterEncodingFilter");

        return bean;
    }

    // 设置ip过滤器，打印访问ip及时间

    @Bean
    public FilterRegistrationBean ipFilter(){

        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new IpFilter());
        bean.addUrlPatterns("/*");
        bean.setName("IpFilter");

        return bean;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/login").setViewName("admin/LoginTest");
//        registry.addViewController("/").setViewName("forward:/static/index.html");
//        registry.addViewController("/admin/backstage").setViewName("admin/backstage");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //告知系统static 当成 静态资源访问
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:");
        registry.addResourceHandler("/preview/static/**").addResourceLocations("classpath:");
    }
}

