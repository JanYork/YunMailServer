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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 信件投递数据
 *
 * @author JanYork
 * @since 2023-06-14 22:59:36
 */
@Data
@ApiModel(value = "信件更新数据", description = "必须登录，字段字数均有限制")
public class LetterChangeDTO {
    /**
     * 信件唯一ID
     */
    @ApiModelProperty(value = "信件唯一ID", example = "1", required = true)
    private Long letterId;
    /**
     * 信件标题
     */
    @ApiModelProperty(value = "信件标题", example = "1", required = true)
    @NotNull(message = "信件标题不能为空")
    @Size(min = 1, max = 20, message = "信件标题长度必须在1-20之间")
    private String letterTitle;
    /**
     * 信件内容 TODO: 优化为富文本
     */
    @ApiModelProperty(value = "信件内容", example = "1", required = true)
    @NotNull(message = "信件内容不能为空")
    @Size(min = 1, max = 5000, message = "信件内容长度必须在1-5000之间")
    private String letterContent;
    /**
     * 信件是否公开
     */
    @ApiModelProperty(value = "信件是否公开", example = "1", required = true)
    @NotNull(message = "是否公开?")
    private Boolean isPublic;
    /**
     * 信件发送时间
     */
    @ApiModelProperty(value = "信件发送时间", example = "1", required = true)
    @NotNull(message = "发信时间不能为空")
    private Date goToTime;
    /**
     * 信件发往地址 TODO：优化为省市区经纬度(P3)
     */
    @ApiModelProperty(value = "信件发往地址", example = "1", required = true)
    @NotNull(message = "地址不能为空")
    @Size(min = 1, max = 50, message = "地址长度必须在1-50之间")
    private String address;
    /**
     * 信件收件人姓名
     */
    @ApiModelProperty(value = "信件收件人姓名", example = "1", required = true)
    @NotNull(message = "收件人不能为空")
    @Size(min = 1, max = 5, message = "收件人长度必须在1-5之间")
    private String addressee;
    /**
     * 信件收信人手机号
     */
    @ApiModelProperty(value = "信件收信人手机号", example = "1", required = true)
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    /**
     * 短信验证码
     */
    @ApiModelProperty(value = "短信验证码", example = "1", required = true)
    @Size(min = 6, max = 6, message = "验证码格式错误")
    private String smsCode;
}
