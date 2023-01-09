package cn.totime.common.email.service;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Map;

/**
 * @author JanYork
 * @date 2023/1/8 22:19
 * @description 邮件发送服务
 */
public interface MailService {
    /**
     * 发送一般邮件
     *
     * @param sendTo  收件人地址
     * @param title   邮件标题
     * @param content 邮件内容
     * @param from    发件人地址
     */
    public void sendSimpleMail(String sendTo, String title, String content, String from);

    /**
     * 发送附件邮件
     *
     * @param sendTo              收件人地址
     * @param title               邮件标题
     * @param content             邮件内容
     * @param attachments<文件名,附件> 附件列表
     * @param from                发件人地址
     */
    public void sendAttachmentsMail(String sendTo, String title, String content, Map<String, File> attachments, String from);


    /**
     * 发送模板邮件
     *
     * @param sendTo              收件人地址
     * @param title               邮件标题
     * @param content<key,内容>     邮件内容
     * @param attachments<文件名,附件> 附件列表
     * @param from                发件人地址
     * @throws MessagingException 邮件发送异常
     */
    public void sendTemplateMail(String sendTo, String title, Map<String, Object> content, Map<String, File> attachments, String from) throws MessagingException;
}