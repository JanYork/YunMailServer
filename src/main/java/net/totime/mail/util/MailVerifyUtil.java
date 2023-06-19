/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import freemarker.template.Template;
import net.totime.mail.enums.KeyType;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.response.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
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
 * @date 2023/06/19
 * @description 邮件验证码工具类
 * @since 1.0.0
 */
@Component
public class MailVerifyUtil {
    @Resource
    private RedisUtil rut;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private FreeMarkerConfigurer freeMarker;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件验证码
     *
     * @param email 邮箱
     * @return {@link String}
     */
    public ApiResponse<Boolean> code(String email, KeyType keyType, Long cacheTime) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (!email.matches(regex)) {
            return ApiResponse.fail(false).message("邮箱格式错误");
        }
        Long incr = rut.incr(email, 60L);
        if (incr > 5) {
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
        try {
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("云寄校验码");
            helper.setText(html, true);
            mailSender.send(message);
            rut.set(keyType.getKey() + email, code, cacheTime);
        } catch (MessagingException e) {
            throw new GloballyUniversalException(500, "邮件服务异常");
        }
        return ApiResponse.ok(true).message("发送成功");
    }

    /**
     * 验证邮件验证码
     *
     * @param email   邮箱
     * @param keyType 密钥类型
     * @param code    验证码
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    public boolean verify(String email, KeyType keyType, String code) {
        String s = (String) rut.get(keyType.getKey() + email);
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        return s.equals(code);
    }
}
