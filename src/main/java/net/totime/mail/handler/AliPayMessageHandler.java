package net.totime.mail.handler;

import com.alibaba.fastjson2.JSON;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.context.SpringBeanContext;
import net.totime.mail.domain.orders.OrdersOperateService;
import net.totime.mail.domain.sponsor.SponsorOperateService;
import net.totime.mail.entity.Orders;
import net.totime.mail.entity.Sponsor;
import net.totime.mail.enums.PayState;
import net.totime.mail.exception.PayException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
            OrdersOperateService oos = SpringBeanContext.getBean(OrdersOperateService.class);
            Orders ordersById = oos.getOrdersById(outTradeNo);
            if (ObjectUtils.isEmpty(ordersById)) {
                SponsorOperateService sos = SpringBeanContext.getBean(SponsorOperateService.class);
                Sponsor sponsorById = sos.getSponsorById(outTradeNo);
                if (ObjectUtils.isNotEmpty(sponsorById)) {
                    sponsorById.setState(PayState.PAID.getValue());
                    sponsorById.setPayTime(new Date());
                    sponsorById.setTradeNo(payMessage.getTradeNo());
                    sponsorById.setSponsorAmount(payMessage.getBuyerPayAmount());
                    if (sos.updateSponsor(sponsorById)){
                        return payService.getPayOutMessage("success", "成功");
                    }
                    throw new PayException("支付宝",payMessage.getOutTradeNo());
                }
            }
            //TODO: 正常订单业务
            return payService.getPayOutMessage("success", "成功");
        }
        return payService.getPayOutMessage("fail", "失败");
    }
}
