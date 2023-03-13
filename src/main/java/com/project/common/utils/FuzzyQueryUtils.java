package com.project.common.utils;

import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletContext;
import java.util.HashMap;


@Component
public class FuzzyQueryUtils {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;


    @Autowired
    private ServletContext servletContext;

    public String setFuzzyWord(String word){

        Object attribute = servletContext.getAttribute("hanziPinyinMap");
        System.out.println(ObjectUtils.isEmpty(attribute));
        if (ObjectUtils.isEmpty(attribute)){
            return word;
        }

        HashMap<String,String> map = (HashMap<String, String>) attribute;

        StringBuilder sb = new StringBuilder();
        sb.append('|');


        for (int i = 0; i < word.length(); i++) {
            char alphabetic = word.charAt(i);
            if ((alphabetic >= 'a' && alphabetic <= 'z') || (alphabetic >= 'A' && alphabetic <= 'Z') || Character.isDigit(alphabetic)){
                sb.append(alphabetic);
                continue;
            }
            String temp = map.get(alphabetic+"");
            if (StringUtils.isNullOrEmpty(temp)){
                sb.append(alphabetic);
            }else {
                sb.append(temp);
                sb.append('|');
            }

        }

        if (sb.charAt(sb.length()-1) != '|'){
            sb.append('|');
        }
        return sb.toString();
    }

}
