package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (Remarks)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Remarks extends Model<Remarks> {
    /**
     * 评论ID
     */
    private Integer id;
}