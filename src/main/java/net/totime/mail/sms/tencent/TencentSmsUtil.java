package net.totime.mail.sms.tencent;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/09
 * @description 短信工具类
 * @since 1.0.0
 */
@Component
public class TencentSmsUtil {
    @Resource
    private SmsClient s;

    /**
     * 发送短信
     *
     * @param req 短信请求对象
     * @return {@link SendSmsResponse} 发送短信响应对象
     */
    public SendSmsResponse sendSms(SendSmsRequest req) {
        try {
            return s.SendSms(req);
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
    }
}
