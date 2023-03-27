package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 信件类型表(LetterType)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
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