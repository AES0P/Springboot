package com.hzero.demo.springboot.springbootdemo.redis.cache;

import com.hzero.demo.springboot.springbootdemo.redis.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Callable;

/**
 * redis cache 实现
 */
@Getter
@Setter
@AllArgsConstructor
public class RedisCache implements Cache {

    private final long liveTime = 86400;//缓存存活时间

    private String name;

    private RedisTemplate<String, Object> redisTemplate;

    private RedisUtil redisUtil;//这里要用到自定义的序列化工具

    @Override
    public void clear() {
        System.out.println("-------緩存清理------");
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    @Override
    public void evict(Object key) {
        System.out.println("-------緩存刪除------");
        final String keyf = key.toString();
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(keyf.getBytes());
            }

        });

    }


    @Override
    public ValueWrapper get(Object key) {
        System.out.println("------缓存获取-------" + key.toString());
        final String keyf = key.toString();
        Object object = null;
        object = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    System.out.println("------缓存不存在-------");
                    return null;
                }

//                return SerializationUtils.deserialize(value);
                return redisUtil.toObject(value);//自定义的反序列化方式
            }
        });
        ValueWrapper obj = (object != null ? new SimpleValueWrapper(object) : null);
        System.out.println("------获取到内容-------" + obj);
        return obj;
    }

    @Override
    public void put(Object key, Object value) {
        System.out.println("-------加入缓存------");
        System.out.println("key----:" + key);
        System.out.println("value----:" + value);
        final String keyString = key.toString();
        final Object valuef = value;
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = keyString.getBytes();
//                byte[] valueb = SerializationUtils.serialize((Serializable) valuef);
                byte[] valueb = redisUtil.toByteArray(valuef);//自定义的序列化方式
                connection.set(keyb, valueb);
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                return 1L;
            }
        });

    }


    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
        return null;
    }

    @Override
    public <T> T get(Object arg0, Class<T> arg1) {
        return null;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

}
