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
 * 短信投递数据
 *
 * @author JanYork
 * @since 2023-06-14 22:59:38
 */
@Data
@ApiModel(value = "短信投递数据", description = "用户ID会根据token自动填充，必须登录，字段字数均有限制")
public class MessageDTO {
    /**
     * 短信内容
     */
    @ApiModelProperty(value = "短信内容", example = "1", required = true)
    @NotNull(message = "内容不能为空")
    @Size(min = 1, max = 300, message = "内容长度在1-300之间")
    private String text;
    /**
     * 短信发送时间
     */
    @ApiModelProperty(value = "短信发送时间", example = "1", required = true)
    @NotNull(message = "发送时间不能为空")
    private Date sendTime;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "1", required = true)
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    /**
     * 短信是否匿名
     */
    @ApiModelProperty(value = "短信是否匿名", example = "true", required = true)
    @NotNull(message = "是否匿名?")
    private Boolean isUnnamed;
    /**
     * 校验码
     */
    @ApiModelProperty(value = "校验码", example = "1", required = true)
    @NotNull(message = "校验码不能为空")
    private String code;
}
