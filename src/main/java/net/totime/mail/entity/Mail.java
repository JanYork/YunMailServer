/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 邮件信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "邮件信息", description = "字段与数据库一致")
public class Mail extends Model<Mail> {
    private static final long serialVersionUID = -1509859450682813622L;
    /**
     * 邮件唯一ID
     */
    @TableId
    @ApiModelProperty(value = "邮件唯一ID", example = "1")
    private Long mailId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 邮件标题
     */
    @ApiModelProperty(value = "邮件标题", example = "测试邮件", required = true)
    @NotNull(message = "邮件标题不能为空")
    @Size(min = 1, max = 30, message = "邮件标题长度在1-30之间")
    private String mailTitle;
    /**
     * 邮件内容
     */
    @ApiModelProperty(value = "邮件内容", example = "测试邮件内容", required = true)
    @NotNull(message = "邮件内容不能为空")
    @Size(min = 1, max = 3000, message = "邮件内容长度在1-3000之间")
    private String mailContent;
    /**
     * 邮件创建时间
     */
    @ApiModelProperty(value = "邮件创建时间", example = "2023-06-14 22:59:37")
    private Date mailCreateTime;
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
    private Date goToTime;
    /**
     * 邮件是否发给自己
     */
    @ApiModelProperty(value = "邮件是否发给自己", example = "true", required = true)
    @NotNull(message = "是否发给自己?")
    private Boolean isYourself;
    /**
     * 邮件发送使用的服务
     */
    @ApiModelProperty(value = "邮件发送使用的服务", example = "1", required = true)
    private Integer useServe;
    /**
     * 邮件发送状态
     */
    @ApiModelProperty(value = "邮件发送状态", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Integer state;
}
