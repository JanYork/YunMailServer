/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 全局商品表(Goods)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "商品信息", description = "全局商品表")
@ApiSupport(author = "JanYork")
public class Goods extends Model<Goods> {
    private static final long serialVersionUID = -4936076321594134230L;
    /**
     * 商品ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "商品ID", example = "10001")
    private Integer id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", example = "云寄信件", required = true)
    private String goodsName;
    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格", example = "0.01", required = true)
    private BigDecimal goodsPrice;
}
