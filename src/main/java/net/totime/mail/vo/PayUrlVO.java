/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/07/07
 * @description 支付信息VO
 * @since 1.0.0
 */
@Data
@ApiModel(value = "支付信息VO")
@NoArgsConstructor
@AllArgsConstructor
public class PayUrlVO {
    /**
     * 支付链接
     */
    @ApiModelProperty(value = "支付链接")
    private String payUrl;
    /**
     * 支付标识
     */
    @ApiModelProperty(value = "支付标识")
    private String payFlag;
}
