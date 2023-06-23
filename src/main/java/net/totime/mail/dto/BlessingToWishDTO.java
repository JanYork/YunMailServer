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

/**
 * 心愿祝福
 *
 * @author JanYork
 * @since 2023-06-14 22:59:34
 */
@Data
@ApiModel(value = "心愿祝福", description = "用户ID会根据token自动填充，必须登录，字段字数均有限制")
public class BlessingToWishDTO {
    /**
     * 祝福语ID
     */
    @ApiModelProperty(value = "祝福语ID", example = "1", required = true)
    @NotNull(message = "祝福语不能为空")
    private Integer blessing;
    /**
     * 心愿ID
     */
    @ApiModelProperty(value = "心愿ID", example = "1324357465786897", required = true)
    @NotNull(message = "心愿不能为空")
    private Long wishId;
}
