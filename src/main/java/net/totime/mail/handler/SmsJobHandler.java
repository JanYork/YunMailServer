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
import net.totime.mail.entity.Message;
import net.totime.mail.entity.MessageOrders;
import net.totime.mail.enums.PayState;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.MessageOrdersService;
import net.totime.mail.service.MessageService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 短信任务处理器
 * @since 1.0.0
 */
@Configuration
public class SmsJobHandler {
    @Resource
    private MessageService ms;
    @Resource
    private MessageOrdersService mos;

    @XxlJob("SmsJobHandler")
    public ApiResponse<String> execute() {
        List<Message> list = ms.list(
                new LambdaQueryWrapper<Message>()
                        .eq(Message::getState, 0)
                        .orderByAsc(Message::getSendTime)
                        .gt(Message::getSendTime, System.currentTimeMillis())
        );
        //TODO:发送短信
        return ApiResponse.ok("短信任务处理器");
    }

    /**
     * 取消订单
     *
     * @return {@link ApiResponse}<{@link String}>
     */
    @XxlJob("MessageCancel")
    public ApiResponse<String> cancel() {
        // 查询订单状态为未支付并且创建时间超过10分钟的订单
        List<MessageOrders> list = mos.list(
                new LambdaQueryWrapper<MessageOrders>()
                        .eq(MessageOrders::getState, PayState.UNPAID.getValue())
                        .le(MessageOrders::getDate, new Date(System.currentTimeMillis() - 15 * 60 * 1000))
        );
        AtomicInteger count = new AtomicInteger();
        list.stream().parallel().forEach(lo -> {
            lo.setState(PayState.CLOSE.getValue());
            boolean r = mos.updateById(lo);
            if (r) {
                count.incrementAndGet();
            }
        });
        return ApiResponse.ok("短信订单取消扫描，本次总发现订单" + list.size() + "个，成功取消" + count.get() + "个");
    }
}
