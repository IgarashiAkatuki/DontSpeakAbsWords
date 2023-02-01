package com.project.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CacheService {

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    /**
     * 获取缓存对象
     * @param name
     * @return
     */
    public Cache getCache(String name){
        return ehCacheCacheManager.getCacheManager().getCache(name);
    }

    /**
     * 将数据存入缓存
     * @param name
     * @param key
     * @param val
     */
    public void setCacheValue(String name, String key, Object val){
        this.getCache(name).put(new Element(key,val));
    }

    /**
     * 判断key是否存在
     * @param name
     * @param key
     * @return
     */
    public boolean isKeyInCache(String name, String key) {
        //获取Cache
        Cache cache = this.getCache(name);

        //ehcache惰性删除机制，缓存失效后key有可能依然存在
        if (cache.isKeyInCache(key)) {
            return cache.get(key) != null;
        } else {
            return false;
        }
    }

    /**
     * 获取key对应的缓存数据
     * @param name
     * @param key
     * @return
     */
    public Object getCacheValue(String name, String key){
        return isKeyInCache(name,key) ? this.getCache(name).get(key) : null;
    }

}
