package com.hzero.demo.springboot.springbootdemo.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class SecurityTestController {

    //    访问地址： http://localhost/dev/success
    @GetMapping("/success")
    @ResponseBody
    public String success() {
        return "认证成功,进入success成功";
    }

    //    访问地址： http://localhost/dev/error
    @GetMapping("/error")
    @ResponseBody
    public String error() {
        return "认证失败,进入error";
    }

    //    访问地址： http://localhost/dev/sign_in
    @GetMapping(value = "/sign_in")
    private String loginPage() {
        return "login";
    }

    //    访问地址： http://localhost/dev/person
    @GetMapping(value = "/person")
    public String personPage() {
        return "person";
    }

    //    访问地址： http://localhost/dev/admin/index
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/admin/index")
    public String adminPage() {
        return "admin/admin";
    }

    //    访问地址： http://localhost/dev/authentication
    @GetMapping(value = "/authentication")
    public String authenticationForm() {

        if (true) {
            return "authenticationForm";
        }

        return "admin/admin";
    }

}
