package com.hzero.demo.springboot.springbootdemo.util.security.login.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在security中验证成功默认跳转到上一次请求页面或者路径为"/"的页面
 * 同样可以自定义：继承SimpleUrlAuthenticationSuccessHandler这个类或者实现AuthenticationSuccessHandler接口。
 * 建议采用继承的方式；SimpleUrlAuthenticationSuccessHandler是默认的处理器，采用继承可以契合里氏替换原则，
 * 提高代码的复用性和避免不必要的错误。
 */
@Component("myLoginSuccessHandler")
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        logger.info("登录成功");
        //这里写你登录成功后的逻辑
    }

}
