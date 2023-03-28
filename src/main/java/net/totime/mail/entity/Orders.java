package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局订单表(Orders)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Orders extends Model<Orders> {
    /**
     * 订单ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单时间
     */
    private Date date;
    /**
     * 商品ID
     */
    private Integer goodsId;
    /**
     * 支付金额
     */
    private Double amount;
}