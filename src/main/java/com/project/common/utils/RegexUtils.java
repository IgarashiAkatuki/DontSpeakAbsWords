package com.project.common.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 正则工具类
public class RegexUtils {

    // 将连续的空格替换为一个下划线
    // 多用于对word的处理
    public static String replaceSpaceToUnderscore(String str){

        if (str.length() > 100){
            str = str.substring(0,100);
        }

        str = str.trim();
        String pattern = "\\s+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);

        str = m.replaceAll("_");
        str = str.toLowerCase();
        return str;
    }

    // 去除多余空格
    // 多用于translation的处理
    public static String removeExtraSpace(String str){

        if (str.length() > 50){
            str = str.substring(0,50);
        }

        str = str.trim();
        String pattern = "\\s+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);

        str = m.replaceAll(" ");
        str = str.toLowerCase();
        return str;
    }
}
