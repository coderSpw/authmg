package com.spw.authmg.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * 集成swagger2
 * @author spw
 * @date 2021/02/15
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        Parameter parameter = parameterBuilder.name("token").description("令牌")
                .modelRef(new ModelRef("String")).parameterType("header")
                .required(false).build();
        List<Parameter> params = new ArrayList<>();
        params.add(parameter);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(params);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("auth权限管理系统 API")
                .description("基于springCloud权限管理系统 API")
                .version("1.0")
                .build();
    }
}
