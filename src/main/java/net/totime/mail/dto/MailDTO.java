package net.totime.mail.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 邮件数据传输对象
 * @since 1.0.0
 */
@Data
public class MailDTO {
    /**
     * 邮件标题
     */
    private String mailTitle;
    /**
     * 邮件内容
     */
    private String mailContent;
    /**
     * 发往地址
     */
    private String goTo;
    /**
     * 邮件是否公开
     */
    private Integer isPublic;
    /**
     * 邮件发送时间
     */
    private Date goToTime;
    /**
     * 邮件是否发给自己
     */
    private Boolean isYourself;
    /**
     * 邮件发送使用的服务
     */
    private Integer useServe;
}