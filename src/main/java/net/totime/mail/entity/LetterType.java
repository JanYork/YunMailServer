/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 信件类型表(LetterType)表实体类
 *
 * @author JanYork
 * @since 2023-03-29 10:46:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LetterType extends Model<LetterType> {
    private static final long serialVersionUID = 4773048251137248174L;
    /**
     * 信件类型ID
     */
    private Integer id;
    /**
     * 信件类型名称
     */
    private String name;
}
