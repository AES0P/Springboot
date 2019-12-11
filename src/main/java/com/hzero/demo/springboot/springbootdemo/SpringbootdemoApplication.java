package com.hzero.demo.springboot.springbootdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@MapperScan(basePackages = "com.hzero.demo.springboot.springbootdemo.web.mapper")
//@EnableEncryptableProperties//如果单独引用的jasypt-spring-boot则需要加上这个注解
@EnableCaching//开启缓存
@ImportResource(locations = "classpath:/redis/Config-Listener.xml")//导入redis 订阅服务xml配置文件
public class SpringbootdemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringbootdemoApplication.class);

    public static void main(String[] args) {

//        //这里也可以配置加解密跟秘钥，实现与配置文件的密文分开放
//        //但不能仅靠写在属性文件里保证安全，最好加入到 系统属性、命令行参数、或者环境变量里。
//        这里可以通过configuration配置，在program arguments一栏中加入--jasypt.encryptor.password=Aesop
//        System.setProperty("jasypt.encryptor.password", "Aesop");

        SpringApplication.run(SpringbootdemoApplication.class, args);
        logger.info("========================启动完毕========================");
    }

}
