package com.spw.authmg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableTransactionManagement(order = 2)
@MapperScan(basePackages = "com.spw.authmg.mapper")
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
