package com.hzero.demo.springboot.springbootdemo.redis.test;

import com.hzero.demo.springboot.springbootdemo.web.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisServiceTest userService;



    @Test
    public void get() {
        // 下面的增删改查中，查询是没有日志输出的，因为它直接从缓存中获取的数据，而添加、修改、删除都是会进入方法内执行具体的业务代码，
        // 然后通过切面去删除掉Redis中的缓存数据。
        // 其中 # 号代表这是一个 SpEL 表达式，此表达式可以遍历方法的参数对象，具体语法可以参考 Spring 的相关文档手册。
        final User user = userService.saveOrUpdate(new User(5, "u5", "p5"));
        log.info("[saveOrUpdate] - [{}]", user);
        final User user1 = userService.get(5);
        log.info("[get] - [{}]", user1);
        userService.delete(5);



    }

}
