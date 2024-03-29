package com.hmall.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: dy
 * @Date: 2024/1/23 20:18
 * @Description:
 */
@FeignClient("user-service")
public interface UserClient {
    @PutMapping("users/money/deduct")
    void deductMoney(@RequestParam("pw") String pw, @RequestParam("amount") Integer amount);
}
