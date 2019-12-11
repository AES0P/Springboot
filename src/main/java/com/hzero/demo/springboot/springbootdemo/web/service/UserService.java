package com.hzero.demo.springboot.springbootdemo.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzero.demo.springboot.springbootdemo.web.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Aesop
 * @since 2019-12-09
 */

public interface UserService extends IService<User> {

    User insert(User user);

    int remove(Integer userId);

    User update(User user);

    User seleceUserById(int id);

    Object listAll(int page, int size);

    IPage<User> selectUsersByAge(Page<User> page, Integer age);

    IPage<User> selectUsersByRole(Page<User> page, String role);

}
