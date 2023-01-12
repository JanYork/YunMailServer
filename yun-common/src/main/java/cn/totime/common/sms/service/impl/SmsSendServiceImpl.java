package cn.totime.common.sms.service.impl;

import cn.totime.common.sms.service.SmsSendService;
import cn.totime.common.sms.utils.SmsUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JanYork
 * @date 2023/1/12 14:46
 * @description 短信发送服务实现类
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {
    @Autowired
    private SmsUtils smsUtils;

    /**
     * 发送短信
     *
     * @param sdkAppId         短信应用ID
     * @param signName         短信签名名称
     * @param templateId       模板ID
     * @param phoneNumberSet   接收短信的手机号码
     * @param templateParamSet 模板参数
     * @return 短信发送响应对象
     * @throws TencentCloudSDKException 腾讯云SDK异常
     */
    @Override
    public SendSmsResponse sendSms(String sdkAppId, String signName, String templateId, String[] phoneNumberSet, String[] templateParamSet) throws TencentCloudSDKException {
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppid(sdkAppId);
        req.setSign(signName);
        req.setTemplateID(templateId);
        req.setPhoneNumberSet(phoneNumberSet);
        req.setTemplateParamSet(templateParamSet);
        return smsUtils.sendSms(req);
    }
}