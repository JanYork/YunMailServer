package cn.totime.api.mail;

import cn.totime.common.email.service.impl.MailServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author JanYork
 * @date 2023/1/9 18:22
 * @description 邮件发送服务接口
 */
@RestController
@RequestMapping("/mail/send")
public class SendMail {
    @Autowired
    private MailServiceImpl mailService;

    @SneakyThrows(Exception.class)
    @RequestMapping("/template")
    public void sendTemplateMail() {
        String sendTo = "747945307@qq.com";
        String title = "测试邮件";
        Map<String, Object> map = Map.of("Text", "JanYork");
        String from = "云寄<timestore@totime.cn>";
        mailService.sendTemplateMail(sendTo, title, map, null,from);
//        mailService.sendSimpleMail(sendTo, title, "测试邮件", from);
    }
}