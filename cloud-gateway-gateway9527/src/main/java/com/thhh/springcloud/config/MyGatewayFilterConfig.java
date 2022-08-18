package com.thhh.springcloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class MyGatewayFilterConfig implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=================== > come in MyGatewayFilterConfig : " + new Date());
        //获取请求参数中的username属性，并判断是否为null
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        if (username == null){
            log.info("=================== > 用户名为NULL，非法用户 /(ㄒoㄒ)/~~");
            //设置错误原因
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            //将请求返回给调用处
            return exchange.getResponse().setComplete();
        }
        //满足条件的请求，当前filter放行请求当下一个filter
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        //设置这个过滤器的级别，数字越小，级别越高，越早执行
        return 0;
    }
}
