package net.totime.mail.domain.sms;

import com.alibaba.cloud.spring.boot.sms.ISmsService;
import com.aliyun.mns.model.Message;
import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import net.totime.mail.sms.SmsReportMessageListenerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 阿里云短信服务
 * @since 1.0.0
 */
public class AliSmsService {
    @Autowired
    private ISmsService smsService;

    @Autowired
    private SmsReportMessageListenerImpl smsReportMessageListener;

    public SendBatchSmsResponse batchsendCheckCode(String code) {
        // 组装请求对象
        SendBatchSmsRequest request = new SendBatchSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        request.setPhoneNumberJson("[\"177********\",\"130********\"]");
        request.setSignNameJson("[\"*******\",\"*******\"]");
        request.setTemplateCode("******");
        request.setTemplateParamJson(
                "[{\"code\":\"" + code + "\"},{\"code\":\"" + code + "\"}]");
        try {
            SendBatchSmsResponse sendSmsResponse = smsService
                    .sendSmsBatchRequest(request);
            return sendSmsResponse;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new SendBatchSmsResponse();
    }

    public SendSmsResponse sendCheckCode(String code) {
        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers("******");
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName("******");
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("******");
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("****TraceId");
        try {
            SendSmsResponse sendSmsResponse = smsService.sendSmsRequest(request);
            return sendSmsResponse;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new SendSmsResponse();
    }

    public QuerySendDetailsResponse querySendDetailsResponse(String telephone) {
        // 组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        // 必填-号码
        request.setPhoneNumber(telephone);
        // 必填-短信发送的日期 支持30天内记录查询（可查其中一天的发送数据），格式yyyyMMdd
        request.setSendDate("20190103");
        // 必填-页大小
        request.setPageSize(10L);
        // 必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        try {
            QuerySendDetailsResponse response = smsService.querySendDetails(request);
            return response;
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return new QuerySendDetailsResponse();
    }

    /**
     * 短信报表
     *
     * @return {@link List}<{@link Message}> 短信消息集合
     */
    public List<Message> smsReport() {
        return smsReportMessageListener.getSmsReportMessageSet();
    }
}