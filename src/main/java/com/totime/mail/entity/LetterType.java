package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (LetterType)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LetterType extends Model<LetterType> {
    /**
     * 信件类型ID
     */
    private Integer id;
    /**
     * 信件类型名称
     */
    private String name;
}