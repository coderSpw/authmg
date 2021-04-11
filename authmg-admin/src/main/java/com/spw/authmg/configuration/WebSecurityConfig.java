package com.spw.authmg.configuration;

import com.spw.authmg.security.JwtAuthenticationFilter;
import com.spw.authmg.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.annotation.Resource;

/**
 * @Description springSecurity配置类
 * @Author spw
 * @Date 2021/3/29
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用自定义身份验证组件
        auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置放行与校验链接
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers("/**/webjars/**").permitAll()
                .antMatchers("/**/druid/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/**/login").permitAll()
                //swagger
                .antMatchers("/**/doc.html").permitAll()
                .antMatchers("/**/swagger-ui.html").permitAll()
                .antMatchers("/**/swagger-resources/**").permitAll()
                .antMatchers("/**/v2/api-docs").permitAll()
                .antMatchers("/**/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers("/**/captcha.jpg").permitAll()
                .antMatchers("/**/actuator/**") .permitAll()
                .anyRequest().authenticated();
        //设置退出登录处理器
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        //设置自定义token验证过滤器
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager())
             , UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 注入spring中，登录认证使用
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
