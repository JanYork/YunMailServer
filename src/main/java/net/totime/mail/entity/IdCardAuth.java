/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 实名认证表(IdCardAuth)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IdCardAuth extends Model<IdCardAuth> {
    /**
     * 实名认证ID
     */
    private Integer id;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 姓名
     */
    private String name;
    /**
     * 其他证明身份图片[JSON]
     */
    private String other;
}
