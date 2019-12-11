package com.hzero.demo.springboot.springbootdemo.redis.cache;


import com.hzero.demo.springboot.springbootdemo.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * redis缓存管理类
 * <p>
 * 1、缓存雪崩：同一时刻redis的key全部失效。
 * 解决方案：给redis的时候有效期设置成随机。
 * 2、缓存击穿：同一时刻缓存没有数据 很多请求落DB
 * 解决方案：第一次请求如果没有数据 直接返回空。然后发一个消息队列 去同步DB。第二次再从缓存取数据。
 * 3、由于redis是默认是惰性删除（key过期不会删除，取key的时候会看是否有效，如果没效则删除，所以会残留很多过期的key）
 * Redis的删除策略可以更正为：定时删除即可解决。
 */
@Component
public class RedisCacheManager {

    //要生成的缓存的名字，如果在任意缓存注解中使用了下面没有定义的缓存区，会报错
    private static final String[] cachePool = {
            "userCache",    //存放单个user
            "allUsersCache"  //存放UserList
    };

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;


    @Bean
    public SimpleCacheManager simpleCacheManager() {

        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

        Set<RedisCache> caches = new HashSet<>();

        for (String cacheName : cachePool) {
            caches.add(new RedisCache(cacheName, redisTemplate, redisUtil));
        }

        simpleCacheManager.setCaches(caches);

        return simpleCacheManager;

    }


}
