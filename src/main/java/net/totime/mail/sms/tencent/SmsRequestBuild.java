/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.sms.tencent;

import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import net.totime.mail.enums.SmsTemplate;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/12
 * @description 短信请求构建
 * @since 1.0.0
 */
public class SmsRequestBuild {
    private static final String APP_ID = "1400791247";
    private static final String SIGN = "云寄";
    private SmsTemplate template;
    private String[] phoneNumber;
    private String[] params;

    public static SmsRequestBuild builder() {
        return new SmsRequestBuild();
    }

    public SmsRequestBuild templateId(SmsTemplate template) {
        this.template = template;
        return this;
    }

    public SmsRequestBuild phoneNumber(String[] phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public SmsRequestBuild params(String[] params) {
        this.params = params;
        return this;
    }

    public SmsTemplate getTemplateId() {
        return template;
    }

    public String[] getPhoneNumber() {
        return phoneNumber;
    }

    public String[] getParams() {
        return params;
    }

    /**
     * 构建请求对象
     *
     * @return {@link SendSmsRequest} 请求
     */
    public SendSmsRequest build() {
        SendSmsRequest req = new SendSmsRequest();
        req.setSignName(SIGN);
        req.setSmsSdkAppId(APP_ID);
        req.setTemplateId(template.getTemplateId());
        req.setPhoneNumberSet(phoneNumber);
        req.setTemplateParamSet(params);
        return req;
    }
}
