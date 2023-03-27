package com.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 许愿表(Wish)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Wish extends Model<Wish> {
    /**
     * 愿望ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 愿望
     */
    private String text;
    /**
     * 图片
     */
    private String image;
    /**
     * 祝福
     */
    private Integer blessing;
    /**
     * 创建时间
     */
    private Date creatTime;
}