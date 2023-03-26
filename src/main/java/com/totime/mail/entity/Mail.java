package com.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (Mail)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Mail extends Model<Mail> {
    /**
     * 邮件唯一ID
     */
    private String mailId;
    /**
     * 用户ID
     */
    private Long userId;
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
    private Date goToDatatime;
    /**
     * 邮件是否发给自己
     */
    private Integer isYourself;
    /**
     * 邮件发送使用的服务
     */
    private Integer useServe;
    /**
     * 邮件发送状态
     */
    private Integer state;
}