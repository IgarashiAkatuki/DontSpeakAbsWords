package com.project.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;


@Component
public class FuzzyQueryUtils {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @ConditionalOnBean(
            name = "redisTemplate"
    )
    public String setFuzzyWord(String word){
        StringBuilder sb = new StringBuilder();
        sb.append('|');


        for (int i = 0; i < word.length(); i++) {
            char alphabetic = word.charAt(i);
            if ((alphabetic >= 'a' && alphabetic <= 'z') || (alphabetic >= 'A' && alphabetic <= 'Z') || Character.isDigit(alphabetic)){
                sb.append(alphabetic);
                continue;
            }
            Object temp = redisTemplate.opsForValue().get(alphabetic+"");
            if (ObjectUtils.isEmpty(temp)){
                sb.append(alphabetic);
            }else {
                sb.append((String) temp);
                sb.append('|');
            }

        }

        if (sb.charAt(sb.length()-1) != '|'){
            sb.append('|');
        }
        return sb.toString();
    }

}
