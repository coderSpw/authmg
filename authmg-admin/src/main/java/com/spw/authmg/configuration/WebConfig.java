package com.spw.authmg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description web全局配置
 * @Author spw
 * @Date 2021/5/27
 */
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor();
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加请求拦截器
        registry.addInterceptor(this.requestInterceptor());
    }
}
