package com.project.common.ehcache;

import com.project.entity.mysql.Translation;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class PlainKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        for (Object param : params) {
            if (param instanceof Translation){
                return ((Translation) param).getWord()+((Translation) param).getTranslation()+method.getName();
            }
        }
        return (String)params[0]+params[1]+method.getName();
    }
}
