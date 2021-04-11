package com.spw.authmg.backup.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description feign请求拦截并设置
 * @Author spw
 * @Date 2021/4/11
 */
public class BaseFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = (String) requestAttributes.getRequest().getAttribute("token");
        if (!StringUtils.isEmpty(token)) {
            requestTemplate.header("token", token);
        }
    }
}
