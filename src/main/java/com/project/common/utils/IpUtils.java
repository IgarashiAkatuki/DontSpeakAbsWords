package com.project.common.utils;

import com.mysql.cj.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

// Ip工具类，用来获得用户的真实ip

public class IpUtils {

    public static String getRealIP(HttpServletRequest httpServletRequest){
        String ip = httpServletRequest.getHeader("X-Real-IP");
        if (StringUtils.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = httpServletRequest.getHeader("x-forwarded-for");
        }
        if (StringUtils.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }
}
