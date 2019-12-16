package com.hzero.demo.springboot.springbootdemo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 由于安全设置策略，这些公共页面无需验证就能访问
 */
@Controller
@RequestMapping
public class PublicPageController {

    //    访问地址： http://localhost/dev/index.html
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    //    访问地址： http://localhost/dev/403
    @GetMapping("/403")
    public String notFoundPage() {
        return "403";
    }

    //    访问地址： http://localhost/dev/404
    @GetMapping("/404")
    public String accessError() {
        return "404";
    }

    //    访问地址： http://localhost/dev/500
    @GetMapping("/500")
    public String internalError() {
        return "500";
    }

}
