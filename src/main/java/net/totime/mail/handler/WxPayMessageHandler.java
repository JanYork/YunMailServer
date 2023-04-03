package net.totime.mail.handler;

import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.wx.bean.WxPayMessage;

import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/29
 * @description 微信支付回调处理器
 * @see PayMessageHandler 支付处理器
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public class WxPayMessageHandler implements PayMessageHandler<WxPayMessage, PayService> {
    private static final String SUCCESS = "SUCCESS";
    private static final String RESULT_CODE = "result_code";
    @Override
    public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, PayService payService) throws PayErrorException {
        if (SUCCESS.equals(payMessage.getPayMessage().get(RESULT_CODE))){
            //TODO：成功后的业务逻辑
            return  payService.getPayOutMessage("SUCCESS", "OK");
        }
        return  payService.getPayOutMessage("FAIL", "失败");
    }
}
