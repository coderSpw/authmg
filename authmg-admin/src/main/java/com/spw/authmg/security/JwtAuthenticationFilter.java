package com.spw.authmg.security;

import com.spw.authmg.security.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description token认证过滤器
 * @Author spw
 * @Date 2021/3/29
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //校验token状态
        SecurityUtils.checkAuthentication(request);
        chain.doFilter(request, response);
    }
}
