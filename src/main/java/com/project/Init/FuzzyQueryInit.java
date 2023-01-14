package com.project.Init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings("all")
@Component
@Slf4j
@ConditionalOnProperty(
        name = "config.enableFuzzyQuery",
        havingValue = "true"
)
public class FuzzyQueryInit {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;
    // 模糊查询模块初始化
    @PostConstruct
    public void FuzzyQueryInit() throws Exception{
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("initResources/Data.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        long start = System.currentTimeMillis();
        int nums = 0;
        System.out.println("=====================");
        System.out.println("正在初始化数据...");

        if (redisTemplate.keys("*").size() <= 20700){
            String str = null;
            while ((str = reader.readLine()) != null){
                String[] s = str.split(" ");
                if (s.length == 2){
                    redisTemplate.opsForValue().set(s[1],s[0]);
                    nums++;
                }
            }
        }else {
            nums = redisTemplate.keys("*").size();
        }

        long end = System.currentTimeMillis();
        System.out.println("数据初始化完成，共加载"+nums+"条数据");
        System.out.println("共耗时"+(end-start)+"ms");
        System.out.println("=====================");
        log.debug("Redis数据初始化完成");
    }

}
