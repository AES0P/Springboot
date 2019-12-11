package com.hzero.demo.springboot.springbootdemo.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzero.demo.springboot.springbootdemo.web.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Aesop
 * @since 2019-12-09
 */
//@Repository
@Mapper //在全局config中配置了mapperscan后，这里就不要再注解了，防止重复扫描
public interface UserMapper extends BaseMapper<User> {


    /**
     * <p>
     * 查询 : 根据 age 查询用户列表，分页显示
     * 注意!!: 如果入参是有多个,需要加注解指定参数名才能在xml中取值
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param age  年龄
     * @return 分页对象
     */
    IPage<User> selectUsersByAge(Page<User> page, @Param("age") Integer age);//通过mapper.xml方式

    @Select("select * from user where role=#{role}")
    IPage<User> selectUsersByRole(Page<User> page, @Param("role") String role);//通过注解方式

}
