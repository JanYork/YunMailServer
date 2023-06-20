/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.open;

import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import freemarker.template.Template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.enums.KeyType;
import net.totime.mail.enums.SmsTemplate;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.sms.tencent.SmsRequestBuild;
import net.totime.mail.sms.tencent.TencentSmsUtil;
import net.totime.mail.util.CodeUtil;
import net.totime.mail.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/20
 * @description 手机验证码接口
 * @since 1.0.0
 */
@RequestMapping("/api/v1")
@RestController
@Api(tags = "[开放]手机验证码接口")
@Slf4j
public class CodeApi {
    @Resource
    private TencentSmsUtil sut;
    private static final String SMS_OK = "Ok";
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private FreeMarkerConfigurer freeMarker;
    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private RedisUtil rut;

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/phone/code")
    @ApiOperation(value = "发送短信验证码", notes = "此接口验证码有效期为3分钟，限制每小时发送10次")
    public ApiResponse<Boolean> phoneCode(String phone) {
        String regex = "^1[3-9]\\d{9}$";
        if (!phone.matches(regex)) {
            return ApiResponse.fail(false).message("手机号格式不正确");
        }
        Long incr = rut.incr(KeyType.OPEN_PHONE_INCR.getKey()+ phone, 3600L);
        if (incr > 10) {
            log.warn("获取过于频繁，手机号：{}", phone);
            return ApiResponse.fail(false).message("获取过于频繁");
        }
        String time = "3";
        String[] phoneNumbers = {phone};
        String code = CodeUtil.generateCode(6);
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(phoneNumbers)
                .templateId(SmsTemplate.NORMAL)
                .params(new String[]{
                        code, time
                })
                .build();
        SendSmsResponse response = sut.sendSms(build);
        if (!SMS_OK.equals(response.getSendStatusSet()[0].getCode())) {
            return ApiResponse.fail(false).message("发送失败");
        }
        long cacheTime = 180L;
        rut.set(KeyType.OPEN_PHONE.getKey() + phone, code, cacheTime);
        return ApiResponse.ok(true).message("发送成功");
    }


    /**
     * 发送邮件验证码
     *
     * @param email 邮箱
     * @return {@link String}
     */
    @GetMapping("/email/code")
    @ApiOperation(value = "发送邮件验证码", notes = "此接口验证码有效期为3分钟，限制每小时发送15次")
    public ApiResponse<Boolean> mailCode(String email) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (!email.matches(regex)) {
            return ApiResponse.fail(false).message("邮箱格式错误");
        }
        Long incr = rut.incr("open_code" + email, 3600L);
        if (incr > 15) {
            return ApiResponse.fail(false).message("获取过于频繁");
        }
        String code = CodeUtil.generateCode(6);
        Template template;
        try {
            template = freeMarker.getConfiguration().getTemplate("code_mail.ftl");
        } catch (IOException e) {
            throw new GloballyUniversalException(500, "邮件模板读取失败");
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("code", code);
        String html;
        try {
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (Exception e) {
            throw new GloballyUniversalException(500, "邮件模板渲染失败");
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
        } catch (MessagingException e) {
            throw new GloballyUniversalException(500, "构建邮件失败");
        }
        long cacheTime = 120;
        try {
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("云寄验证码");
            helper.setText(html, true);
            mailSender.send(message);
            rut.set(KeyType.OPEN_EMAIL_INCR.getKey() + email, code, cacheTime);
        } catch (MessagingException e) {
            throw new GloballyUniversalException(500, "邮件服务异常");
        }
        return ApiResponse.ok(true).message("发送成功");
    }
}
