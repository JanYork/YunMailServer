/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * 此版权个人开发者保留最终归属权与解释权，非版权所有者授权禁止商用与演绎.
 */

package net.totime.mail.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/02
 * @description 分页数据对象
 * @since 1.0.0
 */
@Data
@ApiModel(value = "分页数据对象")
public class PaginationDTO {
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", required = true)
    private Integer page;
    /**
     * 每页大小
     */
    @ApiModelProperty(value = "每页大小", required = true)
    private Integer size;
}
