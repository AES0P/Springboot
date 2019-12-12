package com.hzero.demo.springboot.springbootdemo.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzero.demo.springboot.springbootdemo.web.entity.User;
import com.hzero.demo.springboot.springbootdemo.web.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

/**
 * <p>
 * 前端控制器
 *
 * @author Aesop
 * @since 2019-12-09
 */
@Api(tags = "User")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * http://localhost/dev/user/findUserById?id=34
     *
     * @param id 用户id
     * @return detail 界面标题
     */
    @RequestMapping("findUserById")
    public String findUserById(@RequestParam("id") int id, ModelMap modelMap) {

        modelMap.addAttribute("user", userService.seleceUserById(id));
        modelMap.addAttribute("title", "detail");

        return "detail";
    }

    /**
     * http://localhost/dev/user/selectUserByAge?age=66
     * <p>
     * 根据年龄查询全部
     *
     * @param age
     * @return Page
     */
    @RequestMapping("/selectUserByAge")
    @ResponseBody//简单地展示数据，不跳转界面了，懒得写界面
    public IPage<User> selectUserByAge(@RequestParam("age") Integer age) {
        return userService.selectUsersByAge(new Page<User>(), age);
    }


    /**
     * http://localhost/dev/user/selectUserByRole?role=normal
     * <p>
     * 根据角色查询全部
     *
     * @param role
     * @return Page
     */
    @RequestMapping("/selectUserByRole")
    @ResponseBody//简单地展示数据，不跳转界面了，懒得写界面
    public IPage<User> selectUserByRole(@RequestParam("role") String role) {
        return userService.selectUsersByRole(new Page<User>(), role);
    }

    /**
     * 查询全部
     * <p>
     * http://localhost/dev/user/listAll?page=1&size=6
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/listAll")
    @ResponseBody//简单地展示数据，不跳转界面了，懒得写界面
    public Object listAll(@RequestParam(value = "page", defaultValue = "2") int page,
                          @RequestParam(value = "size", defaultValue = "5") int size) {
        return userService.listAll(page, size);
    }

    /**
     * 添加数据
     * <p>
     * http://localhost/dev/user/insert
     *
     * @param user
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody//简单地展示数据，不跳转界面了，懒得写界面
    public User insert(User user) {

        //启用了MP的自动注入插件，所有不能为空的字段通过插件自动赋值后，数据库不会返回报错了

        user.setUsername("aesop" + new Random().nextInt(1000));
        user.setPassword("pass" + new Random().nextInt(100000));

        return userService.insert(user);
    }

    /**
     * 删除
     * <p>
     * <p>
     * http://localhost/dev/user/remove?userid=8
     *
     * @param userId
     * @return
     */
    @RequestMapping("/remove")
    @ResponseBody//简单地展示数据，不跳转界面了，懒得写界面
    public int remove(@RequestParam("userid") Integer userId) {
        return userService.remove(userId);
    }

    /**
     * 修改
     * <p>
     * http://localhost/dev/user/update?id=33&password=321123
     *
     * @param id
     * @param password
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody//简单地展示数据，不跳转界面了，懒得写界面
    public User updatePasswordById(@RequestParam("id") Integer id, @RequestParam("password") String password) {

        User user = new User();

        user.setId(id);
        user.setPassword(password);
        user.setVersion(0);//设置乐观锁的值，一般需要从数据库先获取，再设置到此

        User User = userService.update(user);

        if (User != null) {
            System.out.println("Update successfully");
            return User;
        } else {
            System.out.println("Update failed due to modified by others");
            return null;
        }


    }


}
