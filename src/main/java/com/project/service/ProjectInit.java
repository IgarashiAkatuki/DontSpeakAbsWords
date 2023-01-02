package com.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

@Slf4j
@Component
@SuppressWarnings("rawtypes")
public class ProjectInit {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    @ConditionalOnProperty(
            name = "config.enableHanzi2Pinyin",
            havingValue = "true"
    )
    @PostConstruct
    public void loadDataToRedis() throws Exception{

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("EmojiData.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));

        long start = System.currentTimeMillis();
        System.out.println("===================");
        System.out.println("开始初始化数据...");

        String str = null;
        int size = 0;

        // 默认使用0号数据库
//        RedisConnection connection = template.getConnectionFactory().getConnection();
//        connection.select(0);

        Set keys = template.keys("*");
        if (keys.isEmpty() || keys.size() == 0){

            while ((str = reader.readLine()) != null){
                String[] s = str.split(" ");
                if (s.length == 3){
                    template.opsForValue().set(s[1],s[2]);
                }
                if (s.length == 2){
                    Object o = template.opsForValue().get(s[1]);
                    if (ObjectUtils.isEmpty(o)){
                        template.opsForValue().set(s[1],"");
                    }

                }
                size++;
            }
        }else {
            size = keys.size();
        }

        long end = System.currentTimeMillis();
        System.out.println("加载完成,共耗时"+(end-start)+"ms");
        System.out.println("共加载"+size+"条数据");
        System.out.println("===================");

        log.debug("Redis数据初始化完成");
    }

}
