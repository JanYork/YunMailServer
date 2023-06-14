/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 邮件任务表(Mail)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Mail extends Model<Mail> {
    /**
     * 邮件唯一ID
     */
    private Long mailId;
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
     * 邮件创建时间
     */
    private Date mailCreateTime;
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
