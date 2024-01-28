package com.hmall.api.config;

import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

import static com.hmall.common.contest.UserInfoConstant.USER_INFO;

/**
 * @Author: dy
 * @Date: 2024/1/22 21:27
 * @Description:
 */
public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLogLevel() {
        return Logger.Level.FULL;
    }

    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Long userId = UserContext.getUser();
                requestTemplate.header(USER_INFO, userId.toString());
            }
        };
    }
}
