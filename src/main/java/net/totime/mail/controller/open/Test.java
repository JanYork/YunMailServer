/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.open;

import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/26
 * @description 测试
 * @since 1.0.0
 */
@RestController
public class Test {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private FreeMarkerConfigurer freeMarker;

    @SneakyThrows
    @RequestMapping("/test")
    public String test() {
        String[] to = {"1345561377@qq.com"};
        Template template1 = freeMarker.getConfiguration().getTemplate("code_mail.ftl");
        Map<String, Object> map1 = new HashMap<>(8);
        map1.put("code", "100876");

        Template template2 = freeMarker.getConfiguration().getTemplate("succeed_mail.ftl");
        Map<String, Object> map2 = new HashMap<>(8);
        map2.put("mail_id", "MAIL3214983723");
        map2.put("mail_title", "来自未来的信");
        map2.put("mail_spacetime", "10374");
        map2.put("mail_time", "2023.01.03");

        Template template3 = freeMarker.getConfiguration().getTemplate("time_mail.ftl");
        Map<String, Object> map3 = new HashMap<>(8);
        map3.put("title", "来自未来的信");
        map3.put("spacetime", "10374");
        map3.put("time", "2023.01.03 12:00");
        map3.put("text", "<p style=\"text-align: left;\">\n" +
                "    测试\n" +
                "</p>\n" +
                "<p style=\"text-align: left;\">\n" +
                "    你好，我是来自未来的你，我在2023年1月3日12点给你发了一封信，希望你能看到。\n" +
                "</p>");

        Template template4 = freeMarker.getConfiguration().getTemplate("audit_mail.ftl");
        Map<String, Object> map4 = new HashMap<>(8);
        map4.put("mail_id", "MAIL3214983723");

        String html1 = FreeMarkerTemplateUtils.processTemplateIntoString(template1, map1);
        String html2 = FreeMarkerTemplateUtils.processTemplateIntoString(template2, map2);
        String html3 = FreeMarkerTemplateUtils.processTemplateIntoString(template3, map3);
        String html4 = FreeMarkerTemplateUtils.processTemplateIntoString(template4, map4);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("747945307@qq.com");
        helper.setTo(to);
        helper.setSubject("验证码模板");
        helper.setText(html1, true);
        mailSender.send(message);
        helper.setSubject("成功投递模板");
        helper.setText(html2, true);
        mailSender.send(message);
        helper.setSubject("时光邮件模板");
        helper.setText(html3, true);
        mailSender.send(message);
        helper.setSubject("邮件审核模板");
        helper.setText(html4, true);
        mailSender.send(message);
        return "success";
    }
}
