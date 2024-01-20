package com.hmall.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: dy
 * @Date: 2024/1/20 16:46
 * @Description:
 */
@Configuration
public class RemoteCallConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
