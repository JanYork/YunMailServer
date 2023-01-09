package cn.totime.common.email.service.impl;

import cn.totime.common.email.service.MailService;
import cn.totime.common.freemarker.FreemarkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @author JanYork
 * @date 2023/1/8 22:27
 * @description 邮件发送服务实现类
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreemarkerUtil freemarkerUtil;

    /**
     * 发送一般邮件
     *
     * @param sendTo  收件人地址
     * @param title   邮件标题
     * @param content 邮件内容
     */
    @Override
    public void sendSimpleMail(String sendTo, String title, String content, String from) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(sendTo);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送附件邮件
     *
     * @param sendTo      收件人地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments 附件列表
     */
    @Override
    public void sendAttachmentsMail(String sendTo, String title, String content, Map<String, File> attachments, String from) {

    }

    /**
     * 发送模板邮件
     *
     * @param sendTo      收件人地址
     * @param title       邮件标题
     * @param content     邮件内容
     * @param attachments 附件列表
     */
    @Override
    public void sendTemplateMail(String sendTo, String title, Map<String, Object> content, Map<String, File> attachments, String from) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(sendTo);
        helper.setSubject(title);
        helper.setText(freemarkerUtil.getTemplateContentMail("test.ftl", content), true);
        mailSender.send(mimeMessage);
    }
}