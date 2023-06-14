/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 时光信件表(Letter)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Letter extends Model<Letter> {
    /**
     * 信件唯一ID
     */
    private Long letterId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 信件标题
     */
    private String letterTitle;
    /**
     * 信件内容
     */
    private String letterContent;
    /**
     * 信件创建时间
     */
    private Date letterCreateTime;
    /**
     * 信件是否公开
     */
    private Integer isPublic;
    /**
     * 信件发送时间
     */
    private Date goToTime;
    /**
     * 信件是否发给自己
     */
    private Integer isYourself;
    /**
     * 信件发往地址
     */
    private String address;
    /**
     * 信件收件人姓名
     */
    private String addressee;
    /**
     * 信件收信人手机号
     */
    private String phone;
    /**
     * 信件备注
     */
    private String remark;
    /**
     * 信件类型
     */
    private Integer letterType;
    /**
     * 信件状态
     */
    private Integer state;
}
