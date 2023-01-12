package cn.totime.api.sms;

import cn.totime.common.sms.enums.SdkAppEnum;
import cn.totime.common.sms.enums.SignNameEnum;
import cn.totime.common.sms.enums.TemplateEnum;
import cn.totime.common.sms.service.impl.SmsSendServiceImpl;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JanYork
 * @date 2023/1/12 18:51
 * @description 短信服务接口
 */
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsSendServiceImpl smsSendService;
    @SneakyThrows(TencentCloudSDKException.class)
    @RequestMapping("/send")
    public SendSmsResponse sendSms(String phone) {
        String[] phoneNumbers = {phone};
        return smsSendService.sendSms(SdkAppEnum.SDK_APP_ID.getId(), SignNameEnum.DEFAULT.getSignName(), TemplateEnum.CODE.getId(),phoneNumbers, new String[]{"123456"});
    }
}