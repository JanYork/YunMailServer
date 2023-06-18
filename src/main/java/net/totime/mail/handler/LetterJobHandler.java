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
import net.totime.mail.entity.Letter;
import net.totime.mail.entity.LetterOrders;
import net.totime.mail.enums.GlobalState;
import net.totime.mail.enums.PayState;
import net.totime.mail.exception.RuntimeExceptionToMsgException;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.LetterOrdersService;
import net.totime.mail.service.LetterService;
import net.totime.mail.sms.tencent.TencentSmsOption;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
public class LetterJobHandler {
    @Resource
    private LetterService ls;
    @Resource
    private LetterOrdersService los;
    @Resource
    private TencentSmsOption tso;

    @XxlJob("LetterJobHandler")
    public ApiResponse<String> execute() {
        List<Letter> list = ls.list(
                new LambdaQueryWrapper<Letter>()
                        .eq(Letter::getState, 0)
                        .orderByAsc(Letter::getGoToTime)
                        .gt(Letter::getGoToTime, System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        );
        //TODO:发送信件
        return ApiResponse.ok("信件任务处理器");
    }

    @XxlJob("LetterRemind")
    public ApiResponse<String> remind() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        List<Letter> list = ls.list(
                new LambdaQueryWrapper<Letter>()
                        .eq(Letter::getState, GlobalState.WAITING_FOR_DELIVERY.getState())
                        .eq(Letter::getGoToTime, date)
        );
        if (tso.sendLetterDailyReport(list.size())) {
            return ApiResponse.ok("信件报表：今日有" + list.size() + "封信件需要投递");
        }
        throw new RuntimeExceptionToMsgException("信件报表发送失败");
    }

    /**
     * 取消订单
     *
     * @return {@link ApiResponse}<{@link String}>
     */
    @XxlJob("LetterCancel")
    public ApiResponse<String> cancel() {
        // 查询订单状态为未支付并且创建时间超过10分钟的订单
        List<LetterOrders> list = los.list(
                new LambdaQueryWrapper<LetterOrders>()
                        .eq(LetterOrders::getState, PayState.UNPAID.getValue())
                        .le(LetterOrders::getDate, new Date(System.currentTimeMillis() - 10 * 60 * 1000))
        );
        AtomicInteger count = new AtomicInteger();
        list.stream().parallel().forEach(lo -> {
            lo.setState(PayState.CLOSE.getValue());
            boolean r = los.updateById(lo);
            if (r) {
                count.incrementAndGet();
            }
        });
        return ApiResponse.ok("信件订单取消扫描，本次总发现订单" + list.size() + "个，成功取消" + count.get() + "个");
    }
}
