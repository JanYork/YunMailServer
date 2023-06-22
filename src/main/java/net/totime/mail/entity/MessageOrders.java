/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 信件订单信息
 *
 * @author JanYork
 * @since 2023-06-16 22:47:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "信件订单信息", description = "字段与数据库一致")
public class MessageOrders extends Model<MessageOrders> {
    private static final long serialVersionUID = -2898299575318016128L;
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "订单ID", example = "1")
    private Integer id;
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", example = "1")
    private String ordersSerial;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 订单时间
     */
    @ApiModelProperty(value = "订单时间", example = "1")
    private Date date;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间", example = "1")
    private Date payTime;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型", example = "1", required = true)
    @NotNull(message = "支付类型不能为空")
    private Integer payType;
    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额", example = "1", required = true)
    @NotNull(message = "支付金额不能为空")
    private BigDecimal amount;
    /**
     * 支付商订单号
     */
    @ApiModelProperty(value = "支付商订单号", example = "1")
    private String tradeNo;
    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态", example = "1", required = true)
    @NotNull(message = "支付状态不能为空")
    private Integer state;
    /**
     * 短信ID
     */
    @ApiModelProperty(value = "短信ID", example = "1", required = true)
    @NotNull(message = "短信ID不能为空")
    private Long messageId;
}
