package com.project.config;

import com.project.filter.CharacterFilter;
import com.project.filter.IpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

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
//        registry.addViewController("/admin/login").setViewName("/LoginTest.html");
//        registry.addViewController("/admin/index").setViewName("/admin.html");
//        registry.addViewController("/").setViewName("forward:/static/admin.html");
//        registry.addViewController("/admin/backstage").setViewName("admin/backstage");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/statics/swagger/");
    }

    // ehCache驱动
    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {
        EhCacheCacheManager em = new EhCacheCacheManager();
        return em;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                .allowedOriginPatterns("*")
                //放行哪些请求方式
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                //.allowedMethods("*") //或者放行全部
                //放行哪些原始请求头部信息
                .allowedHeaders("*")
                //暴露哪些原始请求头部信息
                .exposedHeaders("*");
    }

}

