package com.hmall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: dy
 * @Date: 2024/7/24 19:45
 * @Description:
 */
@ConfigurationProperties(prefix = "hm.cart")
@Component
@Data
public class CartProperties {
    private Integer maxAmount;
}
