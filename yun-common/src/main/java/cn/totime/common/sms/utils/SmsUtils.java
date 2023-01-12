package cn.totime.common.sms.utils;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JanYork
 * @date 2023/1/12 13:13
 * @description 短信发送工具类
 */
@Component
public class SmsUtils {

    @Autowired
    private SmsClient client;

    /**
     * 发送短信
     *
     * @param req 短信请求对象
     * @return 短信发送响应对象
     * @throws TencentCloudSDKException 腾讯云SDK异常
     */
    public SendSmsResponse sendSms(SendSmsRequest req) throws TencentCloudSDKException {
        return client.SendSms(req);
    }
}