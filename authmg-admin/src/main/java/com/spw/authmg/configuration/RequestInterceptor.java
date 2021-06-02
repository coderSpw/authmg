package com.spw.authmg.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spw.authmg.security.utils.JwtTokenUtils;
import com.spw.authmg.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @Description 请求拦截
 * @Author spw
 * @Date 2021/5/27
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //token中解密出用户名
        String token = request.getHeader("Authorization");
        String username = JwtTokenUtils.getUsernameFromToken(token);
        //获取请求方法
        String method = request.getMethod();
        //获取请求参数 & 请求体参数
        String params = JSON.toJSONString(request.getParameterMap());
        String body = this.getBodyData(request);
        //请求参数md5加密
        String paramsMd5 = this.dedumpParam(params, body, (String) null);
        //避免重复请求接口
        String key = "username:" + username + "&method:" + method + "&params:" + paramsMd5;
        long expireTime = 1000L;
        String value = "expire@" + System.currentTimeMillis() + expireTime;
        // redis 互斥锁  防止重复点击
        Boolean result = redisUtils.setNxPx (key, value, expireTime);
        return !Objects.requireNonNull(result) || !result;
    }

    /**
     * 获取请求体
     * @param request
     * @return
     * @throws IOException
     */
    private String getBodyData(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        return body.toString();
    }

    /**
     * 请求参数+请求体参数md5加密
     * @param params 请求参数
     * @param body   请求体
     * @param excludeKeys   排除key
     * @return 加密字符串
     */
    private String dedumpParam(final String params, final String body, String... excludeKeys) {
        String bodyJson = null;
        if (excludeKeys != null && StringUtils.isNotBlank(body)) {
            TreeMap bodyMap = JSONObject.parseObject(body, TreeMap.class);
            List<String> excludeKeyList = Arrays.asList(excludeKeys);
            //排除body中key
            excludeKeyList.forEach(bodyMap::remove);
            bodyJson = JSON.toJSONString(bodyMap);
        }
        //拼接  params + body
        String result = params + bodyJson;
        //MD5加密
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(result.getBytes());
            result = DatatypeConverter.printHexBinary(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("encrypt error: {}", e);
        }
        log.info("param:{} ===> excludeBody:{} === result:{}", params, bodyJson, result);
        return result;
    }
}
