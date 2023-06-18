/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.enums;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/16
 * @description 全局(信件 、 短信 、 邮件)状态枚举
 * @see Enum
 * @since 1.0.0
 */
public enum GlobalState {
    /**
     * 未支付
     */
    WAITING_FOR_PAYMENT(0, "待支付"),
    /**
     * 已失效
     */
    INVALID(1, "已失效"),
    /**
     * 待审核
     */
    WAITING_FOR_AUDIT(2, "待审核"),
    /**
     * 待投递
     */
    WAITING_FOR_DELIVERY(3, "待投递"),
    /**
     * 已投递
     */
    DELIVERED(4, "已投递"),
    /**
     * 已完成
     */
    COMPLETED(5, "已完成"),
    /**
     * 已取消
     */
    CANCELED(6, "已取消"),
    /**
     * 已删除
     */
    DELETED(7, "已删除"),
    /**
     * 人工复审
     */
    MANUAL_REVIEW(8, "人工复审"),
    /**
     * 修改后再次复审
     */
    MANUAL_REVIEW_AGAIN(9, "修改复审");

    private final Integer state;
    private final String desc;

    GlobalState(Integer state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public Integer getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
