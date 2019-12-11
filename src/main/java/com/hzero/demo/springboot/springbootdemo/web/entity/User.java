package com.hzero.demo.springboot.springbootdemo.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Aesop
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class User extends Model<User> {

    //    AUTO : AUTO(0, “数据库ID自增”),
    //    INPUT : INPUT(1, “用户输入ID”),
    //    ID_WORKER : ID_WORKER(2, “全局唯一ID”),
    //    UUID : UUID(3, “全局唯一ID”),
    //    NONE : NONE(4, “该类型为未设置主键类型”),
    //    ID_WORKER_STR : ID_WORKER_STR(5, “字符串全局唯一ID”);
    @TableId(value = "id", type = IdType.AUTO)//使用数据库自带的增长策略,这个注解会影响调用insert方法后返回主键值
    private int id;

    @TableField(fill = FieldFill.INSERT)
    private String role;

    private String username;

    private String password;

    @TableField(fill = FieldFill.INSERT)
    private Integer sexy;

    @TableField(fill = FieldFill.INSERT)
//    @Min(value = 18, message = "18 ban")
    private Integer age;

    @TableField(fill = FieldFill.INSERT)
    private Date birthday;

    @TableField(fill = FieldFill.INSERT)
    private String comments;


    @Version//乐观锁，支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
    private int version;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


}
