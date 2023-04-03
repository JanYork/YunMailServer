package net.totime.mail.handler;

import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/29
 * @description 支付宝支付回调处理器
 * @see PayMessageHandler 支付处理器
 * @since 1.0.0
 */
@Component
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
        Object payId = payService.getPayConfigStorage().getAttach();
        Map<String, Object> message = payMessage.getPayMessage();
        //交易状态
        String tradeStatus = (String) message.get("trade_status");
        //交易完成
        if (SUCCESS.equals(tradeStatus) || FINISHED.equals(tradeStatus)) {
            BigDecimal payAmount = new BigDecimal((String) message.get("total_fee"));
            return payService.getPayOutMessage("success", "成功");
        }
        return payService.getPayOutMessage("fail", "失败");
    }
}
