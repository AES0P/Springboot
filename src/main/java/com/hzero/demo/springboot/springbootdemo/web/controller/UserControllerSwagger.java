package com.hzero.demo.springboot.springbootdemo.web.controller;

import com.hzero.demo.springboot.springbootdemo.web.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "swaggerTest")
@Controller
@RequestMapping("/swagger")
public class UserControllerSwagger {

    @Autowired
    private UserService userService;

    //http://localhost/dev/swagger/findUserById?id=34

    //option的value的内容是这个method的描述，notes是详细描述，response是最终返回的json model。其他可以忽略
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息",
            authorizations = {
                    @Authorization(value = "petstore_auth", scopes = {
                            @AuthorizationScope(scope = "write:swaggerTest", description = "modify swaggerTest in your account"),
                            @AuthorizationScope(scope = "read:swaggerTest", description = "read your swaggerTest")
                    })
            },
            tags = {"swaggerTest",})
    //这里是显示你可能返回的http状态，以及原因。比如404 not found, 303 see other
    @ApiResponses(value = {@ApiResponse(code = 405, message = "Invalid input", response = Void.class)})
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int")
    @RequestMapping("findUserById")
    public String findUserById(@RequestParam("id") int id, ModelMap modelMap) {

        modelMap.addAttribute("user", userService.seleceUserById(id));
        modelMap.addAttribute("title", "detail");

        return "detail";
    }

}
