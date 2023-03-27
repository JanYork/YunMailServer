package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局商品表(Goods)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Goods extends Model<Goods> {
    /**
     * 商品ID
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品价格
     */
    private Double goodsPrice;
}