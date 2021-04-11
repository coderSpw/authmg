package com.spw.authmg.security.utils;

import com.spw.authmg.security.GrantedAuthorityImpl;
import com.spw.authmg.security.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Description JWT工具类
 * @Author spw
 * @Date 2021/3/29
 */
public class JwtTokenUtils {

    /**
     * 密钥
     */
    private static final String secretKey = "abcdefgh";

    /**
     * 权限列表
     */
    private static final String AUTHORITIES = "authorities";

    /**
     * 用户名称
     */
    private static final String USERNAME = Claims.SUBJECT;
    /**
     * 创建时间
     */
    private static final String CREATED = "created";

    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    public static Authentication getAuthenticationFromToken(HttpServletRequest request) {
        Authentication authentication = null;
        //获取请求令牌
        String token = getToken(request);
        //令牌信息校验
        if (Optional.ofNullable(token).isPresent()) {
            if (SecurityUtils.getAuthentication() == null) {
                //从token中获取生命数据
                Claims claims = getClaimsFromToken(token);
                if (claims == null) {
                    return null;
                }
                //从令牌中获取用户名
                String username = getUsernameFromToken(token);
                if (username == null) {
                    return null;
                }
                //令牌过期校验
                if (isTokenExpired(token)) {
                    return null;
                }
                //获取权限列表
                Object authors = claims.get(AUTHORITIES);
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (authors != null && authors instanceof List) {
                    ((List) authors).forEach(o -> {
                        authorities.add(new GrantedAuthorityImpl(
                                (String)((Map) o).get("authority")));
                    });
                }
                //存放用户名、权限列表以及token
                authentication = new JwtAuthenticationToken(username, null, authorities, token);
            } else {
                if (validateToken(token, SecurityUtils.getUsername())) {
                    //token有效放回当前认证信息
                    authentication = SecurityUtils.getAuthentication();
                }
            }
        }
        return authentication;
    }

    /**
     * 验证token
     * @param token
     * @param username
     * @return
     */
    public static Boolean validateToken(String token, String username) {
        //从token中获取username
        String name = getUsernameFromToken(token);
        //用户名匹配且token未过期
        return (username.equals(name) && !isTokenExpired(token));
    }

    /**
     * 验证token是否过期
     * @param token
     * @return
     */
    public static Boolean isTokenExpired(String token) {
        try {
            //从token中获取数据声明
            Claims claims = getClaimsFromToken(token);
            //从数据声明中获取存活时间
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 从token中获取username
     * @param token
     * @return
     */
    public static String getUsernameFromToken (String token) {
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取数据生命
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 从请求头获取token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        final String tokenHead = "Bearer";
        if (!Optional.ofNullable(token).isPresent()) {
            //如果token为null, 从请求头获取token参数
            token = request.getHeader("token");
        } else if (token.contains(tokenHead)) {
            token = token.substring(tokenHead.length());
        }
        return token;
    }

    /**
     * 生成token
     * @param authentication
     * @return
     */
    public static String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME, SecurityUtils.getUsername());
        claims.put(CREATED, new Date());
        claims.put(AUTHORITIES, authentication.getAuthorities());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        Date expiration = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder().setClaims(claims).setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }
}
