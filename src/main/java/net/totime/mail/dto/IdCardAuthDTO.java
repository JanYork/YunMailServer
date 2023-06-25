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

/**
 * 实名认证信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:35
 */
@Data
@ApiModel(value = "实名认证信息", description = "未成年可二要素或填写其他证明身份图片，开启人工审核，成年人必须三要素匹配")
public class IdCardAuthDTO {
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", example = "123456789012345678", required = true)
    @NotNull(message = "身份证号不能为空")
    @Pattern(regexp = "^\\d{17}[\\d|x|X]$", message = "身份证号格式不正确")
    private String idCard;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "张三", required = true)
    @NotNull(message = "姓名不能为空")
    private String name;
    /**
     * 其他证明身份图片[JSON]
     */
    @ApiModelProperty(value = "其他证明身份图片[JSON]", example = "[{id:1,url:https://xxx.com/xxx.jpg}]")
    private String other;
}
