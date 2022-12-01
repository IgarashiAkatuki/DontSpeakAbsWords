package com.project.config;

import com.project.uitls.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

//    定义一个横切关注点
    @Pointcut("execution(* com.project.controller.*.*(..))")
    public void pointcut(){
    }

    @Before(value = "pointcut()")
    public void beforeLog(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println("执行了"+name);
    }
}
