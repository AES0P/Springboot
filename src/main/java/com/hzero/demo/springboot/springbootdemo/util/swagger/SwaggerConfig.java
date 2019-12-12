package com.hzero.demo.springboot.springbootdemo.util.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2 配置实现类
 *
 * @Api 注解可以用来标记 Controller 的功能
 * @ApiOperation 注解用来标记一个方法的作用
 * @ApilmplicitParam 注解用来描述一个参数，可以配置参数的中文含义，也可以给参数设置默认值，这样在接口测试的时候可以避免手动输入
 * @ApilmplicitParams 如果有多个参数，则需要使用多个 @ApilmplicitParam 注解来描述， 多个 @ApilmplicitParam 注解需要放在一个 @ApilmplicitParams 注解中
 * @ApiModel 如果参数是一个对象，则需要在对象所在的类上加上此注解
 * @ApiModelProperty 如果参数是一个对象，则需要在对应的属性上加上此注解，还需要在对象所在的类上加上 @ApiModel
 * @ApiIgnore 注解标识此参数可以忽略
 * <p>
 * * 使用Swagger2只需三步
 * * 1、导入Swaggerr依赖
 * * 2、配置Docket的bean
 * * 3、使用@Api等注解修饰
 * * </p>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API")
                .apiInfo(apiInfo())
                .select()// 选择那些路径和api会生成document,方法需要有ApiOperation注解才能生成接口文档
                //swagger要扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.hzero.demo.springboot.springbootdemo.web.controller"))
//                .apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                .paths(PathSelectors.any())// 对所有路径进行监控
                .build();
//                .securitySchemes(security());// 如何保护我们的Api，有三种验证（ApiKey, BasicAuth, OAuth）
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger")//页面标题
                .description("Springboot接口文档")//描述
                .termsOfServiceUrl("localhost/dev")
                .contact(new Contact("Swagger测试", "localhost/dev/swagger-ui.html", "github.com/AES0P"))//创建人
                .version("1.0.0")//版本号
                .build();
    }


    private List<ApiKey> security() {
        ArrayList<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("token", "token", "header"));
        return apiKeys;
    }

}
