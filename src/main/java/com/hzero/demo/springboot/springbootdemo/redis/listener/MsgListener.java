package com.hzero.demo.springboot.springbootdemo.redis.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 这个类实现listener接收到redis订阅消息后的具体任务
 */
@Component("redisMessageListener")
public class MsgListener implements MessageListener {

    //    @Resource(name = "redisTemplate")
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {

//        System.out.println(message);
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String msg = (String) redisTemplate.getStringSerializer().deserialize(body);
        String topic = (String) redisTemplate.getStringSerializer().deserialize(channel);
        System.out.println("收到topic：" + topic + "发来的消息：" + msg);


    }
}
