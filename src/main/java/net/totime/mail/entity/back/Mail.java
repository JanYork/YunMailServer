/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.totime.mail.enums.MailState;
import net.totime.mail.enums.MailUseServer;

/**
 * 邮件任务表(Mail)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "邮件信息", description = "邮件信息表")
@ApiSupport(author = "JanYork")
public class Mail extends Model<Mail> {
    private static final long serialVersionUID = -8324377863828666746L;
    /**
     * 邮件唯一ID
     */
    @TableId
    @ApiModelProperty(value = "邮件唯一ID")
    private String mailId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 邮件标题
     */
    @ApiModelProperty(value = "邮件标题")
    private String mailTitle;
    /**
     * 邮件内容
     */
    @ApiModelProperty(value = "邮件内容")
    private String mailContent;
    /**
     * 邮件创建时间
     */
    @ApiModelProperty(value = "邮件创建时间")
    private Date mailCreateTime;
    /**
     * 发往地址
     */
    @ApiModelProperty(value = "发往地址")
    private String goTo;
    /**
     * 邮件是否公开
     */
    @ApiModelProperty(value = "邮件是否公开")
    private Boolean isPublic;
    /**
     * 邮件发送时间
     */
    @ApiModelProperty(value = "邮件发送时间")
    private Date goToTime;
    /**
     * 邮件是否发给自己
     */
    @ApiModelProperty(value = "邮件是否发给自己")
    private Boolean isYourself;
    /**
     * 邮件发送使用的服务
     */
    @ApiModelProperty(value = "邮件发送使用的服务")
    private MailUseServer useServe;
    /**
     * 邮件发送状态
     */
    @ApiModelProperty(value = "邮件发送状态")
    private MailState state;
}
