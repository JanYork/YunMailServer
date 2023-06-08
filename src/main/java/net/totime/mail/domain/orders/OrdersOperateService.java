/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain.orders;

import net.totime.mail.entity.Orders;
import net.totime.mail.service.OrdersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/19
 * @description 订单服务类
 * @since 1.0.0
 */
@Service
public class OrdersOperateService {
    @Resource
    private OrdersService ordersService;

    /**
     * 创建订单
     *
     * @param orders 订单信息
     * @return {@link Boolean} 是否创建成功
     */
    public Boolean createOrders(Orders orders) {
        return ordersService.save(orders);
    }

    /**
     * 通过ID获取订单信息
     *
     * @param id 订单ID
     * @return {@link Orders}
     */
    public Orders getOrdersById(Long id) {
        return ordersService.getById(id);
    }

    /**
     * 更新订单信息
     *
     * @param orders 订单信息
     * @return {@link Boolean} 是否更新成功
     */
    public Boolean updateOrders(Orders orders) {
        return ordersService.updateById(orders);
    }
}
