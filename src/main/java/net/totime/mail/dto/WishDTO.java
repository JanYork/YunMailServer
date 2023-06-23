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
import javax.validation.constraints.Size;

/**
 * 心愿投递
 *
 * @author JanYork
 * @since 2023-06-14 22:59:39
 */
@Data
@ApiModel(value = "心愿投递", description = "用户ID会根据token自动填充，必须登录，字段字数均有限制")
public class WishDTO {
    /**
     * 愿望
     */
    @ApiModelProperty(value = "愿望", example = "1", required = true)
    @NotNull(message = "愿望不能为空")
    @Size(min = 10, max = 100, message = "字数必须在10-100之间")
    private String text;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片", example = "1")
    private String image;
    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", example = "1", required = true)
    @NotNull(message = "验证码不能为空")
    private String code;
}
