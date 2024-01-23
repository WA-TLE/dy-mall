package com.hmall.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @Author: dy
 * @Date: 2024/1/23 20:23
 * @Description:
 */
@FeignClient("trade-service")
public interface TradeClient {
    @PutMapping("orders/{orderId}")
    void markOrderPaySuccess(@PathVariable("orderId") Long orderId);
}
