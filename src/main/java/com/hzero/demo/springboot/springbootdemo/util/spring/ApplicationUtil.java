package com.hzero.demo.springboot.springbootdemo.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//加入component注解，让spring扫描到该bean
@Component
public class ApplicationUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public ApplicationUtil() {
    }

    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (applicationContext == null) {
            applicationContext = arg0;
        }

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setAppCtx(ApplicationContext webAppCtx) {
        if (webAppCtx != null) {
            applicationContext = webAppCtx;
        }
    }

    /**
     * 拿到ApplicationContext对象实例后就可以手动获取Bean的注入实例对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) throws ClassNotFoundException {
        return getApplicationContext().getBean(name, clazz);
    }

    public static final Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static final Object getBean(String beanName, String className) throws ClassNotFoundException {
        Class clz = Class.forName(className);
        return getApplicationContext().getBean(beanName, clz.getClass());
    }

    public static boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().isSingleton(name);
    }

    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return getApplicationContext().getAliases(name);
    }

    public static void main(String[] args) {
        /**
         * springboot普通类获取spring容器中的bean
         *
         *User UserDao UserImpl,其中UserImpl中有一个获取User列表的方法getUserList
         * 测试方法：
         * 测试在springboot中根据UserImpl的Class对象调用getUserList方法获取用户列表数据
         */
//        Map param = new HashMap<String,Object>();
//        param.put("name","Tom");
//        List<User> listUser=SpringUtil.getBean(UserImpl.class).getClass()
//                .getDeclaredMethod("getUserList", Map.class)
//                .invoke(UserImpl.class,param);
    }
}
