/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.vo;

import lombok.Data;
import net.totime.mail.enums.PayType;

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
    private Long id;
    /**
     * 赞助用户
     */
    private Long userId;
    /**
     * 赞助用户昵称
     */
    private String userNickname;
    /**
     * 赞助用户头像
     */
    private String userAvatar;
    /**
     * 赞助留言
     */
    private String sponsorSay;
    /**
     * 赞助金额
     */
    private BigDecimal sponsorAmount;
    /**
     * 支付类型
     */
    private PayType payType;
    /**
     * 支付时间
     */
    private Date payTime;
}
