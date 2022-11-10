package com.midsummra.service;

import com.midsummra.utils.IpUtils;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class AfterLog implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记

        Date date = new Date();
        System.out.println("["+sdf.format(date)+"]"
        +"调用了"+target.getClass().getName()+"中的"
        +method.getName()+"方法,返回值为"+returnValue);
    }
}
