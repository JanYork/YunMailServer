/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import net.totime.mail.entity.WishOrders;
import net.totime.mail.enums.PayState;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.WishOrdersService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 信件任务处理器
 * @since 1.0.0
 */
@Configuration
public class WishJobHandler {
    @Resource
    private WishOrdersService wos;

    /**
     * 取消订单
     *
     * @return {@link ApiResponse}<{@link String}>
     */
    @XxlJob("WishCancel")
    public ApiResponse<String> cancel() {
        // 查询订单状态为未支付并且创建时间超过10分钟的订单
        List<WishOrders> list = wos.list(
                new LambdaQueryWrapper<WishOrders>()
                        .eq(WishOrders::getState, PayState.UNPAID.getValue())
                        .le(WishOrders::getDate, new Date(System.currentTimeMillis() - 20 * 60 * 1000))
        );
        AtomicInteger count = new AtomicInteger();
        list.stream().parallel().forEach(lo -> {
            lo.setState(PayState.CLOSE.getValue());
            boolean r = wos.updateById(lo);
            if (r) {
                count.incrementAndGet();
            }
        });
        return ApiResponse.ok("心愿订单取消扫描，本次总发现订单" + list.size() + "个，成功取消" + count.get() + "个");
    }
}
