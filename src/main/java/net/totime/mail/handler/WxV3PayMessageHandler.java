/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.wx.v3.api.WxPayService;
import com.egzosn.pay.wx.v3.bean.response.WxPayMessage;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.context.SpringBeanContext;
import net.totime.mail.entity.*;
import net.totime.mail.enums.GlobalState;
import net.totime.mail.enums.PayState;
import net.totime.mail.exception.PayException;
import net.totime.mail.service.*;
import net.totime.mail.util.PayUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Date;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/29
 * @description 微信支付回调处理器
 * @see PayMessageHandler 支付处理器
 * @since 1.0.0
 */
@Slf4j
public class WxV3PayMessageHandler implements PayMessageHandler<WxPayMessage, WxPayService> {
    private static final String SUCCESS = "SUCCESS";

    /**
     * 处理支付回调消息的处理器接口
     *
     * @param payMessage 支付消息
     * @param context    上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param payService 支付服务
     * @return xml, text格式的消息，如果在异步规则里处理的话，可以返回null
     * @throws PayErrorException 支付错误异常
     */
    @Override
    public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, WxPayService payService) throws PayErrorException {
        Map<String, Object> message = payMessage.getPayMessage();
        String state = (String) message.get("trade_state");
        if (SUCCESS.equals(state)) {
            Long outTradeNo = Long.parseLong(payMessage.getOutTradeNo());
            LetterOrdersService bean = SpringBeanContext.getBean(LetterOrdersService.class);
            // 查询订单
            LetterOrders orders = bean.getOne(
                    new LambdaQueryWrapper<LetterOrders>()
                            .eq(LetterOrders::getOrdersSerial, outTradeNo)
            );
            if (orders == null) {
                log.error("订单不存在，订单号：{}", outTradeNo);
                return payService.getPayOutMessage("fail", "失败");
            }
            if (orders.getState() == PayState.CLOSE.getValue()) {
                log.error("订单已关闭，订单号：{}", outTradeNo);
                return payService.getPayOutMessage("fail", "失败");
            }
            orders.setState(PayState.PAID.getValue());
            orders.setPayTime(new Date());
            orders.setTradeNo(payMessage.getTransactionId());
            orders.setAmount(PayUtils.intToBigDecimal(payMessage.getAmount().getPayerTotal()));
            if (bean.updateById(orders)) {
                LetterService beanService = SpringBeanContext.getBean(LetterService.class);
                Letter letter = beanService.getById(orders.getLetterId());
                if (letter == null) {
                    log.error("信件不存在，信件号：{}", orders.getLetterId());
                    return payService.getPayOutMessage("fail", "失败");
                }
                letter.setState(GlobalState.WAITING_FOR_AUDIT.getState());
                if (!beanService.updateById(letter)) {
                    log.error("信件更新失败，信件号：{}", orders.getLetterId());
                    return payService.getPayOutMessage("fail", "失败");
                }
                BaiDuAiHandler aiHandler = SpringBeanContext.getBean(BaiDuAiHandler.class);
                aiHandler.letterAiCheck(letter);
                return payService.getPayOutMessage("success", "成功");
            }
            throw new PayException("微信", payMessage.getOutTradeNo());
        }
        return payService.getPayOutMessage("FAIL", "失败");
    }

    /**
     * @author JanYork
     * @version 1.0.0
     * @date 2023/06/16
     * @description 赞助订单处理器内部类
     * @see PayMessageHandler
     * @since 1.0.0
     */
    public static class SponsorHandler implements PayMessageHandler<WxPayMessage, WxPayService> {
        /**
         * 处理支付回调消息的处理器接口
         *
         * @param payMessage 支付消息
         * @param context    上下文，如果handler或interceptor之间有信息要传递，可以用这个
         * @param payService 支付服务
         * @return xml, text格式的消息，如果在异步规则里处理的话，可以返回null
         * @throws PayErrorException 支付错误异常
         */
        @Override
        public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, WxPayService payService) throws PayErrorException {
            Map<String, Object> message = payMessage.getPayMessage();
            String state = (String) message.get("trade_state");
            if (SUCCESS.equals(state)) {
                Long outTradeNo = Long.parseLong(payMessage.getOutTradeNo());
                SponsorService bean = SpringBeanContext.getBean(SponsorService.class);
                // 查询订单
                Sponsor sponsor = bean.getById(outTradeNo);
                if (sponsor == null) {
                    log.error("订单不存在，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                sponsor.setState(PayState.PAID.getValue());
                sponsor.setPayTime(new Date());
                sponsor.setTradeNo(payMessage.getTransactionId());
                sponsor.setSponsorAmount(PayUtils.intToBigDecimal(payMessage.getAmount().getPayerTotal()));
                if (bean.updateById(sponsor)) {
                    SimpMessagingTemplate smt = SpringBeanContext.getBean(SimpMessagingTemplate.class);
                    smt.convertAndSendToUser(String.valueOf(sponsor.getUserId()), "/third/pay", "ok");
                    return payService.getPayOutMessage("success", "成功");
                }
                throw new PayException("微信", payMessage.getOutTradeNo());
            }
            return payService.getPayOutMessage("FAIL", "失败");
        }
    }

    /**
     * @author JanYork
     * @version 1.0.0
     * @date 2023/06/16
     * @description 心愿订单处理器内部类
     * @see PayMessageHandler
     * @since 1.0.0
     */
    public static class WishHandler implements PayMessageHandler<WxPayMessage, WxPayService> {
        /**
         * 处理支付回调消息的处理器接口
         *
         * @param payMessage 支付消息
         * @param context    上下文，如果handler或interceptor之间有信息要传递，可以用这个
         * @param payService 支付服务
         * @return xml, text格式的消息，如果在异步规则里处理的话，可以返回null
         * @throws PayErrorException 支付错误异常
         */
        @Override
        public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, WxPayService payService) throws PayErrorException {
            Map<String, Object> message = payMessage.getPayMessage();
            String state = (String) message.get("trade_state");
            if (SUCCESS.equals(state)) {
                Long outTradeNo = Long.parseLong(payMessage.getOutTradeNo());
                WishOrdersService bean = SpringBeanContext.getBean(WishOrdersService.class);
                // 查询订单
                WishOrders orders = bean.getOne(
                        new LambdaQueryWrapper<WishOrders>()
                                .eq(WishOrders::getOrdersSerial, outTradeNo)
                );
                if (orders == null) {
                    log.error("订单不存在，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                if (orders.getState() == PayState.CLOSE.getValue()) {
                    log.error("订单已关闭，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                orders.setState(PayState.PAID.getValue());
                orders.setPayTime(new Date());
                orders.setTradeNo(payMessage.getTransactionId());
                orders.setAmount(PayUtils.intToBigDecimal(payMessage.getAmount().getPayerTotal()));
                if (bean.updateById(orders)) {
                    WishService beanService = SpringBeanContext.getBean(WishService.class);
                    Wish wish = beanService.getById(orders.getWishId());
                    if (wish == null) {
                        log.error("心愿不存在，心愿号：{}", orders.getWishId());
                        return payService.getPayOutMessage("fail", "失败");
                    }
                    wish.setState(GlobalState.WAITING_FOR_AUDIT.getState());
                    if (!beanService.updateById(wish)) {
                        log.error("心愿更新失败，心愿号：{}", orders.getWishId());
                        return payService.getPayOutMessage("fail", "失败");
                    }
                    // TODO：发送心愿AI审核消息通知
                    SimpMessagingTemplate smt = SpringBeanContext.getBean(SimpMessagingTemplate.class);
                    return payService.getPayOutMessage("success", "成功");
                }
                throw new PayException("微信", payMessage.getOutTradeNo());
            }
            return payService.getPayOutMessage("FAIL", "失败");
        }
    }

    /**
     * @author JanYork
     * @version 1.0.0
     * @date 2023/06/16
     * @description 信息订单处理器内部类
     * @see PayMessageHandler
     * @since 1.0.0
     */
    public static class MessageHandler implements PayMessageHandler<WxPayMessage, WxPayService> {
        /**
         * 处理支付回调消息的处理器接口
         *
         * @param payMessage 支付消息
         * @param context    上下文，如果handler或interceptor之间有信息要传递，可以用这个
         * @param payService 支付服务
         * @return xml, text格式的消息，如果在异步规则里处理的话，可以返回null
         * @throws PayErrorException 支付错误异常
         */
        @Override
        public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, WxPayService payService) throws PayErrorException {
            Map<String, Object> message = payMessage.getPayMessage();
            String state = (String) message.get("trade_state");
            if (SUCCESS.equals(state)) {
                Long outTradeNo = Long.parseLong(payMessage.getOutTradeNo());
                MessageOrdersService bean = SpringBeanContext.getBean(MessageOrdersService.class);
                // 查询订单
                MessageOrders orders = bean.getOne(
                        new LambdaQueryWrapper<MessageOrders>()
                                .eq(MessageOrders::getOrdersSerial, outTradeNo)
                );
                if (orders == null) {
                    log.error("订单不存在，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                if (orders.getState() == PayState.CLOSE.getValue()) {
                    log.error("订单已关闭，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                orders.setState(PayState.PAID.getValue());
                orders.setPayTime(new Date());
                orders.setTradeNo(payMessage.getTransactionId());
                orders.setAmount(PayUtils.intToBigDecimal(payMessage.getAmount().getPayerTotal()));
                if (bean.updateById(orders)) {
                    MessageService beanService = SpringBeanContext.getBean(MessageService.class);
                    Message msg = beanService.getById(orders.getMessageId());
                    if (msg == null) {
                        log.error("信息不存在，信息号：{}", orders.getMessageId());
                        return payService.getPayOutMessage("fail", "失败");
                    }
                    msg.setState(GlobalState.WAITING_FOR_AUDIT.getState());
                    if (!beanService.updateById(msg)) {
                        log.error("信息更新失败，信息号：{}", orders.getMessageId());
                        return payService.getPayOutMessage("fail", "失败");
                    }
                    BaiDuAiHandler aiHandler = SpringBeanContext.getBean(BaiDuAiHandler.class);
                    aiHandler.messageAiCheck(msg);
                    return payService.getPayOutMessage("success", "成功");
                }
                throw new PayException("微信", payMessage.getOutTradeNo());
            }
            return payService.getPayOutMessage("FAIL", "失败");
        }
    }
}
