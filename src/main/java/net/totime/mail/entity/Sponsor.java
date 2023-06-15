/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 赞助信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "赞助信息", description = "字段与数据库一致")
public class Sponsor extends Model<Sponsor> {
    private static final long serialVersionUID = -5599816657955968254L;
    /**
     * 赞助表ID
     */
    @TableId
    @ApiModelProperty(value = "赞助表ID", example = "1")
    private Long id;
    /**
     * 赞助用户
     */
    @ApiModelProperty(value = "赞助用户", example = "1", required = true)
    @NotNull(message = "赞助用户不能为空")
    private Long userId;
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
    private Double sponsorAmount;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型", example = "1", required = true)
    @NotNull(message = "支付类型不能为空")
    private Integer payType;
    /**
     * 支付商订单号
     */
    @ApiModelProperty(value = "支付商订单号", example = "1")
    private String tradeNo;
    /**
     * 赞助创建时间
     */
    @ApiModelProperty(value = "赞助创建时间", example = "2023-06-14 22:59:40")
    private Date createTime;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间", example = "2023-06-14 22:59:40")
    private Date payTime;
    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Integer state;
}
