/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.totime.mail.enums.PayType;

import javax.validation.constraints.NotNull;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/19
 * @description 描述
 * @see Model
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "赞助信息", description = "赞助信息表")
@ApiSupport(author = "JanYork")
public class Sponsor extends Model<Sponsor> {
    private static final long serialVersionUID = -5599816657955968254L;
    /**
     * 赞助表ID
     */
    @TableId
    @ApiModelProperty(value = "赞助表ID")
    private Long id;
    /**
     * 赞助用户
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    /**
     * 赞助留言
     */
    @ApiModelProperty(value = "赞助留言")
    private String sponsorSay;
    /**
     * 赞助金额
     */
    @ApiModelProperty(value = "赞助金额")
    private BigDecimal sponsorAmount;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型")
    private PayType payType;
    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态")
    private Integer state;
    /**
     * 支付商订单号
     */
    @ApiModelProperty(value = "支付商订单号")
    private String tradeNo;
    /**
     * 赞助创建时间
     */
    @ApiModelProperty(value = "赞助创建时间")
    private Date createTime;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
}