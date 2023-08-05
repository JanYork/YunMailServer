/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.context.SpringBeanContext;
import net.totime.mail.entity.*;
import net.totime.mail.enums.GlobalState;
import net.totime.mail.enums.PayPollKey;
import net.totime.mail.enums.PayState;
import net.totime.mail.enums.PayType;
import net.totime.mail.exception.PayException;
import net.totime.mail.properties.AliPayProperties;
import net.totime.mail.service.*;
import net.totime.mail.util.RedisUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Date;
import java.util.Map;


/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/29
 * @description 支付宝支付回调处理器
 * @see PayMessageHandler 支付处理器
 * @since 1.0.0
 */
@Slf4j
public class AliPayMessageHandler implements PayMessageHandler<AliPayMessage, AliPayService> {

    private static final String SUCCESS = "TRADE_SUCCESS";
    private static final String FINISHED = "TRADE_FINISHED";


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
    public PayOutMessage handle(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {
        Map<String, Object> message = payMessage.getPayMessage();
        //交易状态
        String tradeStatus = (String) message.get("trade_status");
        //交易完成
        if (SUCCESS.equals(tradeStatus) || FINISHED.equals(tradeStatus)) {
            Long outTradeNo = Long.parseLong(payMessage.getOutTradeNo());
            LetterOrdersService bean = SpringBeanContext.getBean(LetterOrdersService.class);
            // 查询订单
            LetterOrders letterOrders = bean.getOne(
                    new LambdaQueryWrapper<LetterOrders>()
                            .eq(LetterOrders::getOrdersSerial, outTradeNo)
            );
            if (letterOrders == null) {
                log.error("订单不存在，订单号：{}", outTradeNo);
                return payService.getPayOutMessage("fail", "失败");
            }
            if (letterOrders.getState() == PayState.CLOSE.getValue()) {
                log.error("订单已关闭，订单号：{}", outTradeNo);
                return payService.getPayOutMessage("fail", "失败");
            }
            letterOrders.setState(PayState.PAID.getValue());
            letterOrders.setPayTime(new Date());
            letterOrders.setTradeNo(payMessage.getTradeNo());
            letterOrders.setAmount(payMessage.getBuyerPayAmount());
            if (bean.updateById(letterOrders)) {
                LetterService letterService = SpringBeanContext.getBean(LetterService.class);
                Letter letter = letterService.getById(letterOrders.getLetterId());
                if (letter == null) {
                    log.error("信件不存在，信件号：{}", letterOrders.getLetterId());
                    return payService.getPayOutMessage("fail", "失败");
                }
                letter.setState(GlobalState.WAITING_FOR_AUDIT.getState());
                if (!letterService.updateById(letter)) {
                    log.error("信件更新失败，信件号：{}", letterOrders.getLetterId());
                    return payService.getPayOutMessage("fail", "失败");
                }
                BaiDuAiHandler aiHandler = SpringBeanContext.getBean(BaiDuAiHandler.class);
                AliPayProperties ali = SpringBeanContext.getBean(AliPayProperties.class);
                if (ali.getEnablePoll()) {
                    SpringBeanContext.getBean(RedisUtil.class).set(PayPollKey.DEFAULT.getKey() + outTradeNo + PayType.ALI_PAY.getId(), PayState.PAID.getValue(), PayPollKey.DEFAULT.getExpire());
                } else {
                    SimpMessagingTemplate smt = SpringBeanContext.getBean(SimpMessagingTemplate.class);
                    // TODO：STOMP通知前端(P3)
                }
                aiHandler.letterAiCheck(letter);
                return payService.getPayOutMessage("success", "成功");
            }
            throw new PayException("支付宝", payMessage.getOutTradeNo());
        }
        return payService.getPayOutMessage("fail", "失败");
    }

    /**
     * @author JanYork
     * @version 1.0.0
     * @date 2023/06/16
     * @description 赞助订单处理器器内部类
     * @see PayMessageHandler
     * @since 1.0.0
     */
    public static class SponsorHandler implements PayMessageHandler<AliPayMessage, AliPayService> {
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
        public PayOutMessage handle(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {
            Map<String, Object> message = payMessage.getPayMessage();
            //交易状态
            String tradeStatus = (String) message.get("trade_status");
            //交易完成
            if (SUCCESS.equals(tradeStatus) || FINISHED.equals(tradeStatus)) {
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
                sponsor.setTradeNo(payMessage.getTradeNo());
                sponsor.setSponsorAmount(payMessage.getBuyerPayAmount());
                if (bean.updateById(sponsor)) {
                    SimpMessagingTemplate smt = SpringBeanContext.getBean(SimpMessagingTemplate.class);
                    smt.convertAndSendToUser(String.valueOf(sponsor.getUserId()), "/third/pay", "ok");
                    return payService.getPayOutMessage("success", "成功");
                }
                throw new PayException("支付宝", payMessage.getOutTradeNo());
            }
            return payService.getPayOutMessage("fail", "失败");
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
    public static class WishHandler implements PayMessageHandler<AliPayMessage, AliPayService> {

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
        public PayOutMessage handle(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {
            Map<String, Object> message = payMessage.getPayMessage();
            //交易状态
            String tradeStatus = (String) message.get("trade_status");
            //交易完成
            if (SUCCESS.equals(tradeStatus) || FINISHED.equals(tradeStatus)) {
                Long outTradeNo = Long.parseLong(payMessage.getOutTradeNo());
                WishOrdersService bean = SpringBeanContext.getBean(WishOrdersService.class);
                // 查询订单
                WishOrders wishOrders = bean.getOne(
                        new LambdaQueryWrapper<WishOrders>()
                                .eq(WishOrders::getOrdersSerial, outTradeNo)
                );
                if (wishOrders == null) {
                    log.error("订单不存在，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                if (wishOrders.getState() == PayState.CLOSE.getValue()) {
                    log.error("订单已关闭，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                wishOrders.setState(PayState.PAID.getValue());
                wishOrders.setPayTime(new Date());
                wishOrders.setTradeNo(payMessage.getTradeNo());
                wishOrders.setAmount(payMessage.getBuyerPayAmount());
                if (bean.updateById(wishOrders)) {
                    WishService wishService = SpringBeanContext.getBean(WishService.class);
                    Wish wish = wishService.getById(wishOrders.getWishId());
                    if (wish == null) {
                        log.error("心愿不存在，心愿号：{}", wishOrders.getWishId());
                        return payService.getPayOutMessage("fail", "失败");
                    }
                    wish.setState(GlobalState.WAITING_FOR_AUDIT.getState());
                    if (!wishService.updateById(wish)) {
                        log.error("心愿更新失败，心愿号：{}", wishOrders.getWishId());
                        return payService.getPayOutMessage("fail", "失败");
                    }
                    BaiDuAiHandler aiHandler = SpringBeanContext.getBean(BaiDuAiHandler.class);
                    AliPayProperties ali = SpringBeanContext.getBean(AliPayProperties.class);
                    if (ali.getEnablePoll()) {
                        SpringBeanContext.getBean(RedisUtil.class).set(PayPollKey.DEFAULT.getKey() + outTradeNo + PayType.ALI_PAY.getId(), PayState.PAID.getValue(), PayPollKey.DEFAULT.getExpire());
                    } else {
                        SimpMessagingTemplate smt = SpringBeanContext.getBean(SimpMessagingTemplate.class);
                        // TODO：STOMP通知前端(P3)
                    }
                    aiHandler.wishAiCheck(wish);
                    return payService.getPayOutMessage("success", "成功");
                }
                throw new PayException("支付宝", payMessage.getOutTradeNo());
            }
            return payService.getPayOutMessage("fail", "失败");
        }
    }

    /**
     * @author JanYork
     * @version 1.0.0
     * @date 2023/06/16
     * @description 短信订单处理器内部类
     * @see PayMessageHandler
     * @since 1.0.0
     */
    public static class MessageHandler implements PayMessageHandler<AliPayMessage, AliPayService> {

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
        public PayOutMessage handle(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {
            Map<String, Object> message = payMessage.getPayMessage();
            //交易状态
            String tradeStatus = (String) message.get("trade_status");
            //交易完成
            if (SUCCESS.equals(tradeStatus) || FINISHED.equals(tradeStatus)) {
                Long outTradeNo = Long.parseLong(payMessage.getOutTradeNo());
                MessageOrdersService bean = SpringBeanContext.getBean(MessageOrdersService.class);
                // 查询订单
                MessageOrders messageOrders = bean.getOne(
                        new LambdaQueryWrapper<MessageOrders>()
                                .eq(MessageOrders::getOrdersSerial, outTradeNo)
                );
                if (messageOrders == null) {
                    log.error("订单不存在，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                if (messageOrders.getState() == PayState.CLOSE.getValue()) {
                    log.error("订单已关闭，订单号：{}", outTradeNo);
                    return payService.getPayOutMessage("fail", "失败");
                }
                messageOrders.setState(PayState.PAID.getValue());
                messageOrders.setPayTime(new Date());
                messageOrders.setTradeNo(payMessage.getTradeNo());
                messageOrders.setAmount(payMessage.getBuyerPayAmount());
                if (bean.updateById(messageOrders)) {
                    MessageService messageService = SpringBeanContext.getBean(MessageService.class);
                    Message msg = messageService.getById(messageOrders.getMessageId());
                    if (msg == null) {
                        log.error("信息不存在，消息号：{}", messageOrders.getMessageId());
                        return payService.getPayOutMessage("fail", "失败");
                    }
                    msg.setState(GlobalState.WAITING_FOR_AUDIT.getState());
                    if (!messageService.updateById(msg)) {
                        log.error("信息更新失败，消息号：{}", messageOrders.getMessageId());
                        return payService.getPayOutMessage("fail", "失败");
                    }
                    BaiDuAiHandler aiHandler = SpringBeanContext.getBean(BaiDuAiHandler.class);
                    aiHandler.messageAiCheck(msg);
                    return payService.getPayOutMessage("success", "成功");
                }
                throw new PayException("支付宝", payMessage.getOutTradeNo());
            }
            return payService.getPayOutMessage("fail", "失败");
        }
    }
}
