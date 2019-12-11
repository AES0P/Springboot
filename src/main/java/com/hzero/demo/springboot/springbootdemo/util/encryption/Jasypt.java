package com.hzero.demo.springboot.springbootdemo.util.encryption;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

/**
 * 正式项目中不要暴露这个文件以及你的秘钥
 * 在这里生成密文后配置到yml文件中去即可，牢记秘钥
 * <p>
 * 风险:
 * 程序配置文件中，存在解密密文的密码。
 * 因为PBEWithMD5AndDES算法到处都可以找到实现。如果拿到了数据库密文和算法的密码，那么很容易解析出连接数据库的密码。
 * 一般严谨的做法是不会将密文信息与解密工具放在一起，避免程序被获取后，加密算法和数据库密码密文以及解密密码都同时被泄露。
 */
public class Jasypt {

    /**
     * 加密方法
     *
     * @param plainText 需加密文本
     */
    public static void encrypt(String key, String plainText) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        // 加密的算法，这个算法是默认的
        config.setAlgorithm("PBEWithMD5AndDES");
        //加密的密钥，自定义
        if (key.isEmpty()) {
            config.setPassword("Aesop");
        } else {
            config.setPassword(key);
        }
        standardPBEStringEncryptor.setConfig(config);
        String encryptedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println(encryptedText);
    }

    /**
     * 解密方法
     *
     * @param encryptedText 需解密文本
     */
    public static void decrypt(String key, String encryptedText) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        // 解密的算法，需同加密算法相同
        config.setAlgorithm("PBEWithMD5AndDES");
        //解密的密钥，需同加密密钥相同
        if (key.isEmpty()) {
            config.setPassword("Aesop");
        } else {
            config.setPassword(key);
        }
        standardPBEStringEncryptor.setConfig(config);
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        System.out.println(plainText);
    }

    public static void main(String[] args) {
        encrypt("", "root");
        encrypt("", "123456");
        encrypt("", "com.p6spy.engine.spy.P6SpyDriver");
        encrypt("", "jdbc:p6spy:mysql://localhost:3306/springboot?useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true");
    }

}
