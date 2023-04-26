package net.totime.mail.enums;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/14
 * @description 支付状态枚举
 * @see Enum
 * @since 1.0.0
 */
public enum PayState {
    /**
     * 未支付
     */
    UNPAID(0),
    /**
     * 已支付
     */
    PAID(1),
    /**
     * 已退款
     */
    REFUND(2),
    /**
     * 已取消
     */
    CANCEL(3),
    /**
     * 已关闭
     */
    CLOSE(4);

    private final int value;

    PayState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
