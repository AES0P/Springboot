package com.hzero.demo.springboot.springbootdemo.web.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzero.demo.springboot.springbootdemo.web.entity.User;
import com.hzero.demo.springboot.springbootdemo.web.mapper.UserMapper;
import com.hzero.demo.springboot.springbootdemo.web.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Aesop
 * @since 2019-12-09
 */
@Transactional
@Service
@DS("master")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 创建用户，同时使用新的返回值的替换缓存中的值
     * 创建用户后会将allUsersCache缓存全部清空
     */
    @Caching(put = {@CachePut(value = "userCache", key = "#user.id")},
            evict = {@CacheEvict(value = "allUsersCache", allEntries = true)})
    @Override
    public User insert(User user) {

        if (userMapper.insert(user) == 1) {
            return user;
        }

        return null;
    }

    /**
     * 对符合key条件的记录从缓存中移除
     * 删除用户后会将allUsersCache缓存全部清空
     */
    @Caching(
            evict = {
                    @CacheEvict(value = "userCache", key = "#userId"),
                    @CacheEvict(value = "allUsersCache", allEntries = true)
            }
    )
    @Override
    public int remove(Integer userId) {
        return userMapper.deleteById(userId);
    }

    /**
     * 更新用户，同时使用新的返回值的替换缓存中的值
     * 更新用户后会将allUsersCache缓存全部清空
     */
    @Caching(
            put = {@CachePut(value = "userCache", key = "#user.id")},
            evict = {@CacheEvict(value = "allUsersCache", allEntries = true)}
    )
    @Override
    public User update(User user) {

        if (userMapper.updateById(user) == 1) {
            return user;
        }

        return null;
    }


    @Override
    @Cacheable(value = "userCache", key = "#id", unless = "#result == null")
    public User seleceUserById(int id) {
        return userMapper.selectById(id);
    }

    /**
     * value 设置缓存的值
     * key：指定缓存的key，这是指参数id值。key可以使用spEl表达式
     * unless：存缓存的前提
     *
     * @param page
     * @param size
     * @return
     */
    @Cacheable(value = "allUsersCache", key = "'page:' + #page", unless = "#result == null")
    @Override
    @DS("slave_1")
    public Object listAll(int page, int size) {

        Page<User> pageObj = new Page<User>(page, size);
        return userMapper.selectPage(pageObj, null);
    }

    @Override
    @DS("slave_2")
    @Cacheable(value = "allUsersCache", key = "'age:' + #age", unless = "#result.total == 0")
    public IPage<User> selectUsersByAge(Page<User> page, Integer age) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return userMapper.selectUsersByAge(page, age);
    }

    @Override
    @DS("slave_2")
    @Cacheable(value = "allUsersCache", key = "'role:' + #role", unless = "#result.total == 0")
    public IPage<User> selectUsersByRole(Page<User> page, String role) {
        return userMapper.selectUsersByRole(page, role);
    }

//    在调用mp的一些方法的时候始终切换不成功.
//    原因是 mp内置的ServiceImpl在新增,更改删除方法上自带事物.
//    解决办法:
//    1、重写自己的ServiceImpl 取消所有事物注解.
//    2、写自己的service方法,调用baseMapper的方法. 如上面的addUser方法

}
