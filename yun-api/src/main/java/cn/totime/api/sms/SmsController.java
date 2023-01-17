package cn.totime.api.sms;

import cn.totime.common.sms.enums.SdkAppEnum;
import cn.totime.common.sms.enums.SignNameEnum;
import cn.totime.common.sms.enums.TemplateEnum;
import cn.totime.common.sms.service.impl.SmsSendServiceImpl;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "短信服务接口")
@ApiSupport(author = "JanYork")
public class SmsController {
    @Autowired
    private SmsSendServiceImpl smsSendService;

    /**
     * 发送短信(单个)
     *
     * @param phone 手机号
     * @return 发送回调结果
     */
    @SneakyThrows(TencentCloudSDKException.class)
    @RequestMapping("/send/individually")
    @ApiOperation(value = "发送短信(单号)", notes = "短信发送服务接口(单个号码)")
    public SendSmsResponse sendSms(String phone) {
        String[] phoneNumbers = {phone};
        return smsSendService.sendSms(SdkAppEnum.SDK_APP_ID.getId(), SignNameEnum.DEFAULT.getSignName(), TemplateEnum.REGISTER.getId(), phoneNumbers, new String[]{"123456"});
    }

    /**
     * 发送短信(多个)
     *
     * @param phoneNumbers 手机号(多个)
     * @return 发送回调结果
     */
    @SneakyThrows(TencentCloudSDKException.class)
    @RequestMapping("/send/multiple")
    @ApiOperation(value = "发送短信(多号)", notes = "短信发送服务接口(多个号码)")
    public SendSmsResponse sendSms(String[] phoneNumbers) {
        return smsSendService.sendSms(SdkAppEnum.SDK_APP_ID.getId(), SignNameEnum.DEFAULT.getSignName(), TemplateEnum.REGISTER.getId(), phoneNumbers, new String[]{"123456"});
    }
}