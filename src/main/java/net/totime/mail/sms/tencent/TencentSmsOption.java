/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.sms.tencent;

import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import net.totime.mail.enums.SmsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/18
 * @description 腾讯云短信调用中间层
 * @since 1.0.0
 */
@Component
public class TencentSmsOption {
    @Resource
    private TencentSmsUtil sut;
    private static final String SMS_OK = "Ok";
    /**
     * 微信公众号名称
     */
    @Value("${system.wx-mp-name}")
    private String wxMpName;
    /**
     * 微信号
     */
    @Value("${system.wx}")
    private String wx;
    /**
     * 400 电话
     */
    @Value("${system.phone}")
    private String phoneNum;
    /**
     * 运营手机号
     */
    @Value("#{'${system.operator-phone}'.split(',')}")
    private List<String> operatorPhone;


    /**
     * 信件投递成功通知
     *
     * @param id     信件ID
     * @param toTime 预计投递时间
     * @return {@link Boolean}
     */
    public Boolean sendLetterOk(String phone, String id, Date toTime) {
        String[] phoneNumbers = {phone};
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(phoneNumbers)
                .templateId(SmsTemplate.LETTER_DELIVERY)
                .params(new String[]{
                        id, timeFormat(toTime, "yyyy年MM月dd日")
                })
                .build();
        SendSmsResponse response = sut.sendSms(build);
        return SMS_OK.equals(response.getSendStatusSet()[0].getCode());
    }

    /**
     * 信件投递人工复审通知 LETTER_AI_REVIEW
     *
     * @param id     信件ID
     * @param toTime 预计投递时间
     * @return {@link Boolean}
     */
    public Boolean sendLetterAiReview(String phone, String id, Date toTime) {
        String[] phoneNumbers = {phone};
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(phoneNumbers)
                .templateId(SmsTemplate.LETTER_AI_REVIEW)
                .params(new String[]{
                        id, timeFormat(toTime, "yyyy年MM月dd日")
                })
                .build();
        SendSmsResponse response = sut.sendSms(build);
        return SMS_OK.equals(response.getSendStatusSet()[0].getCode());
    }

    /**
     * 信件收件人通知(代发出)
     *
     * @param phone  电话
     * @param toTime 时间
     * @return {@link Boolean}
     */
    public Boolean sendLetterReceiverNotice(String phone, Date toTime) {
        String[] phoneNumbers = {phone};
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(phoneNumbers)
                .templateId(SmsTemplate.LETTER_RECEIVER_NOTICE)
                .params(new String[]{
                        timeFormat(toTime, "yyyy年MM月dd日"),
                        phoneNum
                })
                .build();
        SendSmsResponse response = sut.sendSms(build);
        return SMS_OK.equals(response.getSendStatusSet()[0].getCode());
    }

    /**
     * 短信提交后通知
     *
     * @param phone 电话
     * @param id    信件ID
     * @param time  投递时间
     */
    public Boolean sendMessageSubmit(String phone, String id, Date time) {
        String[] phoneNumbers = {phone};
        // 第二个参数
        String secondParam = timeFormat(time, "yyyy-MM-dd");
        // 判断是否是今天
        if (secondParam.equals(timeFormat(new Date(), "yyyy-MM-dd"))) {
            secondParam = "今天";
        }
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(phoneNumbers)
                .templateId(SmsTemplate.SMS_DELIVERY)
                .params(new String[]{
                        id,
                        secondParam
                })
                .build();
        SendSmsResponse response = sut.sendSms(build);
        return SMS_OK.equals(response.getSendStatusSet()[0].getCode());
    }

    /**
     * 取信码通知
     *
     * @param phone 电话
     * @param userId 用户ID
     * @param code 取信码
     */
    public Boolean sendMessageCode(String phone, String userId, String code) {
        String[] phoneNumbers = {phone};
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(phoneNumbers)
                .templateId(SmsTemplate.LETTER_CODE)
                .params(new String[]{
                        userId,
                        code,
                        "公众号",
                        "["+wxMpName+"]",
                        wxMpName
                })
                .build();
        SendSmsResponse response = sut.sendSms(build);
        return SMS_OK.equals(response.getSendStatusSet()[0].getCode());
    }

    /**
     * 实体信件日报表
     */
    public Boolean sendLetterDailyReport(int count) {
        String[] phone = operatorPhone.toArray(new String[0]);
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(phone)
                .templateId(SmsTemplate.OPERATE_DAILY)
                .params(new String[]{
                        String.valueOf(count)
                })
                .build();
        SendSmsResponse response = sut.sendSms(build);
        return SMS_OK.equals(response.getSendStatusSet()[0].getCode());
    }


    /**
     * 时间格式
     *
     * @param date   日期
     * @param format 格式
     * @return {@link String}
     */
    private String timeFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
