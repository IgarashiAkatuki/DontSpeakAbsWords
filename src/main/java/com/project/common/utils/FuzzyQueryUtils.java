package com.project.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
                sb.append("_");
            }

        }

        if (sb.charAt(sb.length()-1) == '_'){
            sb.delete(sb.length()-1,sb.length());
        }
        return sb.toString();
    }
}
