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
 * 愿望祝福表(Blessing)表实体类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Blessing extends Model<Blessing> {
    /**
     * 祝福ID
     */
    private Integer id;
    /**
     * 祝福分类ID
     */
    private Integer categoryId;
    /**
     * 祝福内容
     */
    private String content;
}
