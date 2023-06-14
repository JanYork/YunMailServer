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
 * 短信任务表(Message)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Message extends Model<Message> {
    /**
     * 短信ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 短信内容
     */
    private String text;
    /**
     * 短信发送时间
     */
    private Date sendTime;
    /**
     * 短信创建时间
     */
    private Date createTime;
    /**
     * 短信是否匿名
     */
    private Integer isUnnamed;
    /**
     * 短信状态
     */
    private Integer state;
}
