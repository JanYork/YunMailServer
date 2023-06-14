/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain.orders;

import net.totime.mail.entity.back.OrdersBuilder;
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
     * @param ordersBuilder 订单信息
     * @return {@link Boolean} 是否创建成功
     */
    public Boolean createOrders(OrdersBuilder ordersBuilder) {
        return ordersService.save(ordersBuilder);
    }

    /**
     * 通过ID获取订单信息
     *
     * @param id 订单ID
     * @return {@link OrdersBuilder}
     */
    public OrdersBuilder getOrdersById(Long id) {
        return ordersService.getById(id);
    }

    /**
     * 更新订单信息
     *
     * @param ordersBuilder 订单信息
     * @return {@link Boolean} 是否更新成功
     */
    public Boolean updateOrders(OrdersBuilder ordersBuilder) {
        return ordersService.updateById(ordersBuilder);
    }
}
