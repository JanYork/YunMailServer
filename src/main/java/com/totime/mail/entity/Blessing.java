package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 愿望祝福表(Blessing)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:32
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