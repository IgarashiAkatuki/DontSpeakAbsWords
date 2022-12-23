package com.project.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletContext;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private ServletContext context;

    @Pointcut("execution(* com.project.controller.*.*(..))")
    public void pointcut(){
    }

    @Before(value = "pointcut()")
    public void beforeLog(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        Date date = new Date();
//        System.out.println("["+date+"]执行了"+name);
        log.debug("==> "+name);
    }

    // 记录API调用次数
    @After(value = "pointcut()")
    public void afterLog(JoinPoint joinPoint){

        String name = joinPoint.getSignature().getName();

        if ("getTranslationsFromPersistence".equals(name)){
            Object temp = context.getAttribute("selectTranslCount");
            if (ObjectUtils.isEmpty(temp)){
                context.setAttribute("selectTranslCount",1);
            }else {
                context.setAttribute("selectTranslCount",(int)temp+1);
            }

        }else if ("submitTranslationToTemp".equals(name)){
            Object temp = context.getAttribute("addTranslCount");
            if (ObjectUtils.isEmpty(temp)){
                context.setAttribute("addTranslCount",1);
            }else {
                context.setAttribute("addTranslCount",(int)temp+1);
            }

        }else if ("generateRandomQuestionnaire".equals(name)){
            Object temp = context.getAttribute("QuestionnaireCount");
            if (ObjectUtils.isEmpty(temp)){
                context.setAttribute("QuestionnaireCount",1);
            }else {
                context.setAttribute("QuestionnaireCount",(int)temp+1);
            }

        }else if ("addWords".equals(name)){
            Object temp = context.getAttribute("addWordCount");
            if (ObjectUtils.isEmpty(temp)){
                context.setAttribute("addWordCount",1);
            }else {
                context.setAttribute("addWordCount",(int)temp+1);
            }
        }

    }

}
