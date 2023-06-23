/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 心愿祝福信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:34
 */
@Data
public class WishBlessingVO {
    /**
     * 祝福ID
     */
    @ApiModelProperty(value = "祝福ID", example = "1")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1432543643654754", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 祝福语ID
     */
    @ApiModelProperty(value = "祝福语ID", example = "1", required = true)
    @NotNull(message = "祝福语不能为空")
    private Integer blessing;
    /**
     * 祝福语
     */
    @ApiModelProperty(value = "祝福语", example = "1", required = true)
    private String blessingText;
    /**
     * 心愿ID
     */
    @ApiModelProperty(value = "心愿ID", example = "1324357465786897", required = true)
    @NotNull(message = "心愿不能为空")
    private Long wishId;
}
