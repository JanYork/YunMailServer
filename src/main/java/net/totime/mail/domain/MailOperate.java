/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/19
 * @description 邮件发送操作
 * @since 1.0.0
 */
@Service
@Slf4j
public class MailOperate {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private FreeMarkerConfigurer freeMarker;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送投递成功邮件
     *
     * @param to 收件人
     */
    public void sendSucceedMail(String to, String mailId, String mailTitle, String mailSpacetime, Date time) throws IOException, TemplateException, MessagingException {
        Template template = freeMarker.getConfiguration().getTemplate("succeed_mail.ftl");
        Map<String, Object> map = new HashMap<>(8);
        map.put("mail_id", mailId);
        map.put("mail_title", mailTitle);
        map.put("mail_spacetime", mailSpacetime);
        map.put("mail_time", timeFormat(time, "yyyy.MM.dd"));
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("云寄时空信投递成功");
        helper.setText(html, true);
        mailSender.send(message);
    }

    /**
     * 发送二次审核邮件
     *
     * @param to 收件人
     */
    public void sendAuditMail(String to, String mailId) {
        try {
            Template template = freeMarker.getConfiguration().getTemplate("audit_mail.ftl");
            Map<String, Object> map = new HashMap<>(2);
            map.put("mail_id", mailId);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("云寄时空信待审核通知");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送二次审核通知邮件失败：" + "收件人：" + to + "，邮件ID：" + mailId, e);
        }
    }

    /**
     * 发送投递成功后收信人邮件通知
     *
     * @param to 收件人
     */
    public void sendToSucceedMail(String to, String mailId, String mailTitle, String mailSpacetime, Date time) {
        try {
            Template template = freeMarker.getConfiguration().getTemplate("to_succeed_mail.ftl");
            Map<String, Object> map = new HashMap<>(8);
            map.put("mail_id", mailId);
            map.put("mail_title", mailTitle);
            map.put("mail_spacetime", mailSpacetime);
            map.put("mail_time", timeFormat(time, "yyyy.MM.dd"));
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("云寄通知：您将在未来收到一封时光信件");
            helper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送投递成功后收信人邮件通知失败：" + "收件人：" + to + "，邮件ID：" + mailId, e);
        }
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
