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
 * 信件物流DTO
 *
 * @author JanYork
 * @since 2023-05-12 15:27:22
 */
@Data
@ApiModel(value = "信件物流查询信息")
public class LogisticsDTO {
    /**
     * 信件ID
     */
    @ApiModelProperty(value = "信件ID", required = true)
    @NotNull(message = "信件不能为空")
    private String letterId;
    /**
     * 物流单号
     */
    @ApiModelProperty(value = "物流单号", required = true)
    @NotNull(message = "物流单号不能为空")
    private String logisticsId;
    /**
     * 物流商户['SF','YZ','YT'...]
     */
    @ApiModelProperty(value = "物流商户['SF','YZ','YT'...]", required = true)
    @NotNull(message = "物流商户不能为空")
    private String logisticsCode;
}
