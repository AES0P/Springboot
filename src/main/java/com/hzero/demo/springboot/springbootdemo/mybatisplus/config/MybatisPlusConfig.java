package com.hzero.demo.springboot.springbootdemo.mybatisplus.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import net.sf.jsqlparser.statement.delete.Delete;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aesop
 * @since 2019/12/09
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.hzero.demo.springboot.springbootdemo.web.mapper")
public class MybatisPlusConfig {

//    /**
//     * SQL执行效率插件 该插件 3.2.0 以上版本移除。推荐使用第三方扩展 p6spy 执行SQL分析打印 功能
//     * 参数：maxTime SQL 执行最大时长，超过自动停止运行，有助于发现问题。
//     * 参数：format SQL SQL是否格式化，默认false。
//     * 该插件只用于开发环境，不建议生产环境使用。
//     */
//    @Bean
//    @Profile({"dev","test"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }


    /**
     * 插件配置：
     * 1、分页设置
     * 2、攻击 SQL 阻断解析器
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        //--------------分页设置-------------
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(50);


        //--------------攻击 SQL 阻断解析器 阻止恶意的全表更新删除-------------
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser() {
            @Override
            public void processDelete(Delete delete) {
                // 如果你想自定义做点什么，可以重写父类方法像这样子
                if ("user".equals(delete.getTable().getName())) {
                    // 自定义跳过某个表，其他关联表可以调用 delete.getTables() 判断
                    return;
                }
                super.processDelete(delete);
            }
        });
        paginationInterceptor.setSqlParserList(sqlParserList);


        return paginationInterceptor;
    }

    /**
     * 乐观锁
     * <p>
     * 实现原理：
     * 取出记录时，获取当前version
     * 更新时，带上这个version
     * 执行更新时， set version = newVersion where version = oldVersion
     * 如果version不对，就更新失败
     * <p>
     * 配置步骤：1、写插件bean；2、在entity中注解实体字段 @Version （必须）支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * 整数类型下 newVersion = oldVersion + 1
     * newVersion 会回写到 entity 中
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     * 示例SQL：update tbl_user set name = 'update',version = 3 where id = 100 and version = 2
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


}
