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
import java.math.BigDecimal;

/**
 * 赞助订单构建必须信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:40
 */
@Data
@ApiModel(value = "赞助订单构建信息", description = "用于构建赞助订单的信息")
public class SponsorDTO{
    /**
     * 赞助留言
     */
    @ApiModelProperty(value = "赞助留言", example = "1")
    private String sponsorSay;
    /**
     * 赞助金额
     */
    @ApiModelProperty(value = "赞助金额", example = "1", required = true)
    @NotNull(message = "金额不能为空")
    private BigDecimal sponsorAmount;
}
