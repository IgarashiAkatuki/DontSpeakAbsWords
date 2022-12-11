package com.project.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(* com.project.controller.*.*(..))")
    public void pointcut(){
    }

    @Before(value = "pointcut()")
    public void beforeLog(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        Date date = new Date();
        System.out.println("["+date.getTime()+"]执行了"+name);
    }
}
