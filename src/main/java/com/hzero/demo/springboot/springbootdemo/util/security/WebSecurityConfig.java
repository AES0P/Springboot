package com.hzero.demo.springboot.springbootdemo.util.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 关于SpringSecurity官方提供的登录页面,没有人会使用这个页面来进行身份认证，都是自定义登录的页面来进行身份认证
 * 但是处理登录逻辑是使用的这个接口
 * <p>
 * 使用它要完成 SpringSecurity最基本的配置,首先创建一个类WebSecurityConfg来继WebSecurityConfigurerAdapter这个适配器
 * 继承之后实现http的configure方法。
 */
@Configuration//实现该类后，在web容器启动的过程中该类实例对象会被WebSecurityConfiguration类处理
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler myLoginSuccessHandler; //认证成功结果处理器

    @Autowired
    private AuthenticationFailureHandler myLoginFailureHandler; //认证失败结果处理器

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers(
                "/resources/**/*.html",
                "/resources/**/*.js",
                "/resources/**/*.css",
                "/resources/**/*.txt",
                "/resources/**/*.png",
                "/**/*.bmp",
                "/**/*.gif",
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.ico");
//        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //关于HttpSecurity使用的是链式编程，其中http.xxxx.and.yyyyy这种写法和http.xxxx;http.yyyy写法意义一样。
        http.authorizeRequests()
                //antMatchers是设置路径,通常这里设置的是控制器(controller)上的请求路径
                .antMatchers("/500").permitAll()//permitAll是允许任何人访问,通常对公共的页面我们都设置的permitAll来进行访问的
                .antMatchers("/403").permitAll()
                .antMatchers("/404").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")//指定权限为ADMIN才能访问, @PreAuthorize注解可以替代
                .antMatchers("/person").hasAnyRole("ADMIN", "USER")//指定多个权限都能访问，逗号隔开
                .anyRequest().authenticated() //其他所有路径都需要权限校验.
                .and()//and()方法类似于xml配置中的结束标签，返回的对象还是HttpSecurity，当我们配置完成一段配置就要使用and来结束
                .formLogin()//登录设置
//                .loginPage("/login").permitAll().passwordParameter("username").passwordParameter("password")
//                .loginProcessingUrl("/authentication")
                .successForwardUrl("/success")  //登录成功跳转地址
                .failureForwardUrl("/error") //登录失败跳转地址permitAll() //采用的是表单认证方式,自定义登录页面的url
                .successHandler(myLoginSuccessHandler)//使用自定义的成功结果处理器
                .failureHandler(myLoginFailureHandler)//使用自定义失败的结果处理器
                .and()
                .logout().//登出设置
                logoutUrl("/logout").logoutSuccessUrl("/index")
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .invalidateHttpSession(true)
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear)
                .and()
                .rememberMe().rememberMeParameter("remeber")//开启记住我功能,登陆成功以后，将cookie发给浏览器保存，以后访问页面带上这个cookie，只要通过检查就可以免登陆,点击注销会删除cookie
                .and()
//                .addFilterAfter(new MyFittler(), LogoutFilter.class)//在过滤器链中插入自己的过滤器，addFilterBefore加在对应的过滤器之前addFilterAfter之后，addFilterAt加在过滤器同一位置
//                .addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();//关闭跨站请求伪造保护
    }


    /**
     * 自定义认证策略
     *
     * @return
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //内存验证方式
        auth.inMemoryAuthentication()  //在Spring Security 5.0中新增了多种加密方式，页改变了密码的格式
                .withUser("aesop").password(passwordEncoder().encode("123")).roles("ADMIN", "USER")
                .and()
                .withUser("user1").password(passwordEncoder().encode("123")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("123")).roles("USER");

////        JDBC验证方式
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .withDefaultSchema()
//                .withUser("linyuan").password("123").roles("USER").and()
//                .withUser("linyuan2").password("456").roles("ADMIN");

    }

    /**
     * 这里使用了对登录的密码进行加密处理用到了spring security最强大的
     * 加密器 BCryptPasswordEncoder 随机生成盐(用户的密码和盐混合在一起),
     * 每次生成的密码盐都不一样,这也是spring security BCryptPasswordEncoder强大之处
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
