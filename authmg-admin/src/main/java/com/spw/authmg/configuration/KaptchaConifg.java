package com.spw.authmg.configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description 验证码配置类
 * @Author spw
 * @Date 2021/3/29
 */
@Configuration
public class KaptchaConifg {

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
