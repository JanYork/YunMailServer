/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 短信任务表DTO
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@Data
public class MessageDTO {
    /**
     * 短信内容
     */
    private String text;
    /**
     * 短信发送时间
     */
    private Date sendTime;
    /**
     * 短信是否匿名
     */
    private Integer isUnnamed;
}
