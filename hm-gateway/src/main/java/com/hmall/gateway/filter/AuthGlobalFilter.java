package com.hmall.gateway.filter;

import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.hmall.common.contest.UserInfoConstant.USER_INFO;

/**
 * @Author: dy
 * @Date: 2024/1/27 18:18
 * @Description:
 */
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final JwtTool jwtTool;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //  1. 获取请求
        ServerHttpRequest request = exchange.getRequest();

        //  2. 判断是否需要拦截
        if (isExclude(request.getPath().toString())) {
            return chain.filter(exchange);
        }

        //  3.1 获取 token
        List<String> headers = request.getHeaders().get("authorization");

        //  3.2 可能未登录, 无法获得 token
        String token = null;
        if (headers != null && !headers.isEmpty()) {
            token = headers.get(0);
        }


        //  4. 校验并解析 token  这里可能出现异常
        //  Long userId = jwtTool.parseToken(token);
        Long userId = null;

        try {
            userId = jwtTool.parseToken(token);
        } catch (Exception e) {
            //  设置相应状态码
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //  5. 拿到用户信息, 保存
        String userInfo = userId.toString();
        ServerWebExchange swe = exchange.mutate()
                .request(builder -> builder.header(USER_INFO, userInfo))
                .build();
        System.out.println("userId = " + userId);

        //  6. 放行
        return chain.filter(swe);

    }

    private boolean isExclude(String path) {
        for (String excludePath : authProperties.getExcludePaths()) {
            if (antPathMatcher.match(excludePath, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
