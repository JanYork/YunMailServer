
package net.totime.mail.interceptor;

import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.common.exception.PayErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/29
 * @description 支付宝回调信息拦截器
 * @see PayMessageInterceptor 支付处理器
 * @since 1.0.0
 */
@Slf4j
public class AliPayMessageInterceptor implements PayMessageInterceptor<AliPayMessage, AliPayService> {

    /**
     * 拦截支付消息
     *
     * @param payMessage 支付回调消息
     * @param context    上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param payService 支付服务
     * @return true代表OK，false代表不OK并直接中断对应的支付处理器
     * @throws PayErrorException PayErrorException*
     * @see PayMessageHandler 支付处理器
     */
    @Override
    public boolean intercept(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService) throws PayErrorException {
        String outTradeNo = payMessage.getOutTradeNo();
        return true;
    }
}
