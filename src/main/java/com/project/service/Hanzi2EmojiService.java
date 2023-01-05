package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@ConditionalOnProperty(
        name = "config.enableHanzi2Pinyin",
        havingValue = "true"
)
public class Hanzi2EmojiService {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    public String Hanzi2Emoji(String str){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            Object o = template.opsForValue().get(str.charAt(i)+"");
            if (ObjectUtils.isEmpty(o)){
                sb.append(str.charAt(i));
            }else {
                sb.append((String) o);
            }
        }

        return sb.toString();
    }
}
