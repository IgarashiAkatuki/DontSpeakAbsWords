package com.project.Init;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

@SuppressWarnings("all")
@Component
@Slf4j
@ConditionalOnProperty(
        name = "config.enableFuzzyQuery",
        havingValue = "true"
)
public class FuzzyQueryInit {

    @Autowired
    private ServletContext servletContext;

//    @Autowired
//    @Qualifier("redisTemplate")
//    private RedisTemplate redisTemplate;
//    // 模糊查询模块初始化
//    @PostConstruct
//    public void FuzzyQueryInit() throws Exception{
//        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("initResources/Data.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//
//        long start = System.currentTimeMillis();
//        int nums = 0;
//        System.out.println("=====================");
//        System.out.println("正在初始化数据...");
//
//        if (redisTemplate.keys("*").size() <= 20000){
//            String str = null;
//            while ((str = reader.readLine()) != null){
//                String[] s = str.split(" ");
//                if (s.length == 2){
//                    redisTemplate.opsForValue().set(s[1],s[0]);
//                    nums++;
//                }
//            }
//        }else {
//            nums = redisTemplate.keys("*").size();
//        }
//
//        long end = System.currentTimeMillis();
//        System.out.println("数据初始化完成，共加载"+nums+"条数据");
//        System.out.println("共耗时"+(end-start)+"ms");
//        System.out.println("=====================");
//        log.debug("Redis数据初始化完成");
//    }


    @PostConstruct
    public void loadSource(){
        try{
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("initResources/Data.txt");
            HashMap<String, String> hanziPinyinMap = new HashMap<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            try(reader) {
                log.debug("====================");
                log.debug("开始加载汉字拼音映射表...");
                long start = System.currentTimeMillis();

                int num = 0;
                String temp = null;
                while ((temp = reader.readLine()) != null){
                    String[] s = temp.split(" ");
                    if (s.length == 2){
                        hanziPinyinMap.put(s[1],s[0]);
                        num++;
                    }
                }
                servletContext.setAttribute("hanziPinyinMap",hanziPinyinMap);
                long end = System.currentTimeMillis();
                log.debug("加载完成，共加载[" + num + "]条数据");
                log.debug("加载完成，共耗时[" + (end-start) + "]ms");
                log.debug("====================");
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("/!\\汉字拼音映射表初始化错误/!\\");
        }
    }
}
