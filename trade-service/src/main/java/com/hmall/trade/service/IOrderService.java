package com.hmall.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmall.trade.domain.dot.OrderFormDTO;
import com.hmall.trade.domain.po.Order;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dy
 * @since 2023-05-05
 */
public interface IOrderService extends IService<Order> {

    Long createOrder(OrderFormDTO orderFormDTO);

    void markOrderPaySuccess(Long orderId);
}
