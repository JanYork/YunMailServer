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
import net.totime.mail.enums.PayType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/21
 * @description 赞助信息视图对象
 * @since 1.0.0
 */
@Data
public class SponsorInfoVO {
    /**
     * 赞助表ID
     */
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
    private BigDecimal sponsorAmount;
    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型", example = "1", required = true)
    @NotNull(message = "支付类型不能为空")
    private PayType payType;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间", example = "2023-06-14 22:59:40")
    private Date payTime;

    /**
     * 赞助用户昵称
     */
    @ApiModelProperty(value = "赞助用户昵称", example = "1")
    private String userNickname;
    /**
     * 赞助用户头像
     */
    @ApiModelProperty(value = "赞助用户头像", example = "1")
    private String userAvatar;
}
