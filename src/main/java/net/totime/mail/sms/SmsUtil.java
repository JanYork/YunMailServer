package net.totime.mail.sms;

import com.alibaba.cloud.spring.boot.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 短信工具类
 * @since 1.0.0
 */
@Component
public class SmsUtil {
    @Resource
    private ISmsService smsService;

    /**
     * 单条短信发送
     *
     * @param request 请求
     * @return {@link SendSmsResponse} 响应
     */
    public SendSmsResponse sendSingleSms(SendSmsRequest request) {
        try {
            return smsService.sendSmsRequest(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量短信发送
     *
     * @param request 请求
     * @return {@link SendSmsResponse} 响应
     */
    public SendBatchSmsResponse sendBatchSms(SendBatchSmsRequest request) {
        try {
            return smsService.sendSmsBatchRequest(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}