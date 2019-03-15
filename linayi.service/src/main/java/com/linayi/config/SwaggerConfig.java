package com.linayi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("accessToken").description("accessToken")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的accessToken参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)//加.genericModelSubstitutes(DeferredResult.class)来避免对返回参数的解析不规范的问题
                .useDefaultResponseMessages(true)
                // 指定当前服务的host也就是swagger访问地址
                .forCodeGeneration(true)
                .groupName("first")//这是最顶上下拉框那里可选择分组 如果分组多的话
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())//这是调用下面那个类，固定配置
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linayi.controller"))//扫描com路径下的api文档
                // 过滤生成链接(any()表示所有url路径)
                .paths(PathSelectors.any())//路径判断 如果PathSelectors.any()改成or(regex("/api/.*"))就是过滤/api/下的接口
                .build();
    }
    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("标题：Swagger 开发规范")//标题
                .description("描述：Swaggger 开发规范详")//描述
                .termsOfServiceUrl("")
                /*.termsOfServiceUrl("http://blog.csdn.net/xxoo00xx00/article/details/77163399")*///（不可见）条款地址
                .version("1.6.6")//版本号
                .build();
    }
    }
