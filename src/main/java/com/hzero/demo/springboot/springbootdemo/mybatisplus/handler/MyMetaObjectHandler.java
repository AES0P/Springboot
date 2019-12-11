package com.hzero.demo.springboot.springbootdemo.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;


/**
 * mp提供的自动填充功能
 * <p>
 * 1、字段必须声明TableField注解，属性fill选择对应策略，该申明告知 Mybatis-Plus 需要预留注入 SQL 字段
 * 2、填充处理器MyMetaObjectHandler 在 Spring Boot 中需要声明@Component 注入
 * 3、必须使用父类的setFieldValByName()或者setInsertFieldValByName/setUpdateFieldValByName方法，否则不会根据注解FieldFill.xxx来区分
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("role", "normal", metaObject);//版本号3.0.6以及之前的版本
        this.setFieldValByName("sexy", new Random().nextInt(2), metaObject);
        this.setFieldValByName("age", new Random().nextInt(101), metaObject);
        this.setFieldValByName("birthday", new Date(), metaObject);
        this.setFieldValByName("comments", "nothing", metaObject);
        //this.setInsertFieldValByName("operator", "Jerry", metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("start update fill ....");
        this.setFieldValByName("birthday", new Date(), metaObject);
        //this.setUpdateFieldValByName("operator", "Tom", metaObject);//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
    }
}
