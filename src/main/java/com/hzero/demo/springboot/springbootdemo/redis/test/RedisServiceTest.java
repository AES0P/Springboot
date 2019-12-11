package com.hzero.demo.springboot.springbootdemo.redis.test;

import com.hzero.demo.springboot.springbootdemo.redis.util.RedisUtil;
import com.hzero.demo.springboot.springbootdemo.web.entity.User;
import com.hzero.demo.springboot.springbootdemo.web.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试redis缓存注解和订阅服务
 */
@Transactional
@Service
public class RedisServiceTest {

    private static final Map<Long, User> DATABASES = new HashMap<>();

    static {
        DATABASES.put(1L, new User(1, "u1", "p1"));
        DATABASES.put(2L, new User(2, "u2", "p2"));
        DATABASES.put(3L, new User(3, "u3", "p3"));
    }

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Cacheable(value = "userCache", key = "#id")
    public User get(int id) {
        // TODO 我们就假设它是从数据库读取出来的
        redisUtil.sendMessage("aesop", "进入 get 方法");
        return DATABASES.get((long) id);
    }

    @CachePut(value = "userCache", key = "#user.id")
    public User saveOrUpdate(User user) {
        DATABASES.put((long) user.getId(), user);
        redisUtil.sendMessage("aesop", "进入 saveOrUpdate 方法");
        return user;
    }

    @CacheEvict(value = "userCache", key = "#id")
    public void delete(int id) {
        DATABASES.remove((long) id);
        redisUtil.sendMessage("aesop", "进入 delete 方法");
    }

}
