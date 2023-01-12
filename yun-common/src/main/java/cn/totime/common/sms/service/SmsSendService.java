package cn.totime.common.sms.service;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

/**
 * @author JanYork
 * @date 2023/1/12 14:48
 * @description 短信发送服务接口
 */
public interface SmsSendService {
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
    SendSmsResponse sendSms(String sdkAppId, String signName, String templateId, String[] phoneNumberSet, String[] templateParamSet) throws TencentCloudSDKException;
}