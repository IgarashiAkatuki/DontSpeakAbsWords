package com.midsummra.utils;

import com.mysql.cj.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    //将空格替换为下划线
    public static String replaceSpaceToUnderscore(String s){
        if (!StringUtils.isNullOrEmpty(s)){
            s = s.trim();
            String pattern = "\\s+";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(s);
            s = m.replaceAll("_");
        }
        return s;
    }

    //去除多余空格
    public static String removeExtraSpace(String s){
        if (!StringUtils.isNullOrEmpty(s)){
            s = s.trim();
            String pattern = "\\s+";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(s);
            s = m.replaceAll(" ");
        }
        return s;
    }
}
