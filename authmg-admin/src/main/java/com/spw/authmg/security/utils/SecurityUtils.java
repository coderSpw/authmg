package com.spw.authmg.security.utils;

import com.spw.authmg.security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @Description springSecurity工具类
 * @Author spw
 * @Date 2021/3/29
 */
public class SecurityUtils {

    /***
     * 获取认证信息
     * @param request
     */
    public static void checkAuthentication(HttpServletRequest request) {
        //获取token,根据token获取登录认证信息
        Authentication authentication = JwtTokenUtils.getAuthenticationFromToken(request);
        //设置登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 获取上下文认证信息
     * @return
     */
    public static Authentication getAuthentication () {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户名
     * @return
     */
    public static String getUsername() {
        //从上下文中获取认证信息
        Authentication authentication = getAuthentication();
        return getUsername(authentication);
    }


    /**
     * 从认证中获取用户名
     * @param authentication
     * @return
     */
    public static String getUsername(Authentication authentication) {
        String username = null;
        if (Optional.ofNullable(authentication).isPresent()) {
            Object principal = authentication.getPrincipal();
            if (Optional.ofNullable(principal).isPresent()
                    && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    public static JwtAuthenticationToken login(HttpServletRequest request, String username, String password
                                    , AuthenticationManager authenticationManager) {
        JwtAuthenticationToken token = new JwtAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //执行认证
        Authentication authentication = authenticationManager.authenticate(token);
        //认证存入上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成令牌
        token.setToken(JwtTokenUtils.generateToken(authentication));
        return token;
    }
}
