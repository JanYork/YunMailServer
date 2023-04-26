package net.totime.mail.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/14
 * @description 支付类型
 * @see Enum
 * @since 1.0.0
 */
public enum PayType {
    /**
     * 支付宝
     */
    ALI_PAY(1, "支付宝"),
    /**
     * 微信
     */
    WX_PAY(2, "微信");
    @EnumValue
    private final Integer id;
    @JsonValue
    private final String desc;
    PayType(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }
    public Integer getId() {
        return id;
    }
    public String getDesc() {
        return desc;
    }
}
