/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.handler;

import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.wx.v3.bean.response.WxPayMessage;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.context.SpringBeanContext;
import net.totime.mail.domain.orders.OrdersOperateService;
import net.totime.mail.domain.sponsor.SponsorOperateService;
import net.totime.mail.entity.Orders;
import net.totime.mail.entity.Sponsor;
import net.totime.mail.enums.PayState;
import net.totime.mail.exception.PayException;
import net.totime.mail.util.PayUtils;
import org.apache.commons.lang3.ObjectUtils;
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
public class WxV3PayMessageHandler implements PayMessageHandler<WxPayMessage, PayService> {
    private static final String SUCCESS = "SUCCESS";

    /**
     * 处理
     *
     * @param payMessage 支付信息
     * @param context    上下文
     * @param payService 支付服务
     * @return {@link PayOutMessage}
     * @throws PayErrorException 支付错误异常
     */
    @Override
    public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, PayService payService) throws PayErrorException {
        Map<String, Object> message = payMessage.getPayMessage();
        log.error("Message: {}", message);
        String state = (String) message.get("trade_state");
        if (SUCCESS.equals(state)) {
            Long outTradeNo = Long.parseLong(payMessage.getOutTradeNo());
            OrdersOperateService oos = SpringBeanContext.getBean(OrdersOperateService.class);
            Orders ordersById = oos.getOrdersById(outTradeNo);
            if (ObjectUtils.isEmpty(ordersById)) {
                SponsorOperateService sos = SpringBeanContext.getBean(SponsorOperateService.class);
                Sponsor sponsorById = sos.getSponsorById(outTradeNo);
                if (ObjectUtils.isNotEmpty(sponsorById)) {
                    sponsorById.setState(PayState.PAID.getValue());
                    sponsorById.setPayTime(new Date());
                    sponsorById.setTradeNo(payMessage.getTransactionId());
                    sponsorById.setSponsorAmount(PayUtils.intToBigDecimal(payMessage.getAmount().getPayerTotal()));
                    if (sos.updateSponsor(sponsorById)) {
                        SimpMessagingTemplate smt = SpringBeanContext.getBean(SimpMessagingTemplate.class);
                        smt.convertAndSendToUser(String.valueOf(sponsorById.getUserId()), "/third/pay", "ok");
                        return payService.successPayOutMessage(payMessage);
                    }
                    throw new PayException("微信", payMessage.getOutTradeNo());
                }
            }
            //TODO: 正常订单业务
        }
        return payService.getPayOutMessage("FAIL", "订单不存在");
    }
}
