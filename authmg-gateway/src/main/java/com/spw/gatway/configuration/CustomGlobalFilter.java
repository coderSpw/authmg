package com.spw.gatway.configuration;

import com.alibaba.fastjson.JSON;
import com.spw.authmg.core.http.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

/**
 * @Description 网关全局过滤器
 * @Author spw
 * @Date 2021/4/11
 */
@Slf4j
@Order(0)
@Component
public class CustomGlobalFilter implements GlobalFilter {
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange).then(
            Mono.fromRunnable(() -> {
                Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                if (startTime != null) {
                    String path = exchange.getRequest().getURI().getPath();
                    log.info("请求路径：{} ===》请求耗时：{}", path, System.currentTimeMillis() - startTime);
                }
            })
        );
    }
}
