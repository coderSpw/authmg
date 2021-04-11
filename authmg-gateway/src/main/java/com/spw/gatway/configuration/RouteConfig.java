package com.spw.gatway.configuration;

/**
 * @Description gateway配置类
 * @Author spw
 * @Date 2021/4/11
 */

import com.spw.gatway.constants.GateWayConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class RouteConfig {

    /**
     * cors配置
     * @return
     */
    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                HttpHeaders headers = exchange.getResponse().getHeaders();
                headers.add("Access-Control-Allow-Origin", GateWayConstants.ALLOWED_ORIGIN);
                headers.add("Access-Control-Allow-Methods", GateWayConstants.ALLOWED_METHODS);
                headers.add("Access-Control-Max-Age", GateWayConstants.MAX_AGE);
                headers.add("Access-Control-Allow-Headers", GateWayConstants.ALLOWED_HEADERS);
                headers.add("Access-Control-Expose-Headers", GateWayConstants.ALLOWED_Expose);
                headers.add("Access-Control-Allow-Credentials", "true");
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    exchange.getResponse().setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(exchange);
        };
    }
}
