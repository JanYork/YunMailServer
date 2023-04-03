package net.totime.mail.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/30
 * @description 订单通用实体类
 * @since 1.0.0
 */
@Data
public class QueryOrder {
    /**
     * 支付id
     */
    private Integer payId;
    /**
     * 支付平台订单号
     */
    private String tradeNo;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 账单时间
     */
    private Date billDate;
    /**
     * 账单类型
     */
    private String billType;
    /**
     * 支付平台订单号或者账单时间
     */
    private Object tradeNoOrBillDate;
    /**
     * 商户订单号或者账单类型
     */
    private String outTradeNoBillType;
    /**
     * 交易类型
     */
    private String transactionType;
}