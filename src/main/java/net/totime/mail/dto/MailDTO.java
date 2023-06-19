/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 邮件投递数据
 *
 * @author JanYork
 * @since 2023-06-14 22:59:37
 */
@Data
@ApiModel(value = "邮件投递数据", description = "用户ID会根据token自动填充，必须登录，字段字数均有限制")
public class MailDTO {
    /**
     * 邮件标题
     */
    @ApiModelProperty(value = "邮件标题", example = "测试邮件", required = true)
    @NotNull(message = "邮件标题不能为空")
    @Size(min = 1, max = 30, message = "邮件标题长度在1-30之间")
    private String mailTitle;
    /**
     * 邮件内容 TODO: 优化为富文本
     */
    @ApiModelProperty(value = "邮件内容", example = "测试邮件内容", required = true)
    @NotNull(message = "邮件内容不能为空")
    @Size(min = 1, max = 3000, message = "邮件内容长度在1-3000之间")
    private String mailContent;
    /**
     * 发往地址
     */
    @ApiModelProperty(value = "发往地址", example = "123456@qq.com", required = true)
    @NotNull(message = "发往地址不能为空")
    @Email(message = "邮件格式不正确")
    private String goTo;
    /**
     * 邮件是否公开
     */
    @ApiModelProperty(value = "邮件是否公开", example = "true", required = true)
    @NotNull(message = "是否公开?")
    private Boolean isPublic;
    /**
     * 邮件发送时间
     */
    @ApiModelProperty(value = "邮件发送时间", example = "2023-06-14 22:59:37")
    @NotNull(message = "发送时间不能为空")
    private Date goToTime;
    /**
     * 邮件是否发给自己
     */
    @ApiModelProperty(value = "邮件是否发给自己", example = "true", required = true)
    @NotNull(message = "是否发给自己?")
    private Boolean isYourself;
    /**
     * 邮件验证码
     */
    @ApiModelProperty(value = "邮件验证码", example = "123456", required = true)
    @NotNull(message = "邮件验证码不能为空")
    @Size(min = 6, max = 6, message = "邮件验证码长度为6")
    private String mailCode;
}
