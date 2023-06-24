/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.enums.PayPollKey;
import net.totime.mail.enums.PayState;
import net.totime.mail.enums.PayType;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.util.RedisUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/25
 * @description 支付轮询接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user/pay/poll")
@Api(tags = "[用户]支付状态轮询接口")
public class PayPollApi {
    @Resource
    private RedisUtil rut;

    /**
     * 支付宝支付轮询
     *
     * @param orderId 订单号
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/ali")
    @ApiOperation(value = "支付宝支付轮询", notes = "轮询支付成功后状态将会被删除，只有1次机会回调成功")
    public ApiResponse<Boolean> aliPayPoll(String orderId) {
        String key = PayPollKey.DEFAULT.getKey() + orderId + PayType.ALI_PAY.getId();
        Integer i = (Integer) rut.get(key);
        if (i == null) {
            return ApiResponse.ok(false).message("订单状态不存在").code(PayState.CLOSE.getValue());
        }
        if (i == PayState.PAID.getValue()) {
            rut.delete(key);
            return ApiResponse.ok(true).message("ok").code(PayState.PAID.getValue());
        }
        return ApiResponse.ok(false).message("go to").code(PayState.UNPAID.getValue());
    }

    /**
     * 微信支付轮询
     *
     * @param orderId 订单号
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/wx")
    @ApiOperation(value = "微信支付轮询", notes = "轮询支付成功后状态将会被删除，只有1次机会回调成功")
    public ApiResponse<Boolean> wxPayPoll(String orderId) {
        String key = PayPollKey.DEFAULT.getKey() + orderId + PayType.WX_PAY.getId();
        Integer i = (Integer) rut.get(key);
        if (i == null) {
            return ApiResponse.ok(false).message("订单状态不存在").code(PayState.CLOSE.getValue());
        }
        if (i == PayState.PAID.getValue()) {
            rut.delete(key);
            return ApiResponse.ok(true).message("ok").code(PayState.PAID.getValue());
        }
        return ApiResponse.ok(false).message("go to").code(PayState.UNPAID.getValue());
    }
}
