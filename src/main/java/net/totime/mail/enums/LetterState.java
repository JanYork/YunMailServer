package net.totime.mail.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 邮件状态
 * @see Enum
 * @since 1.0.0
 */
public enum LetterState {
    /**
     * 未发送
     */
    NOT_SEND(0, "未发送"),
    /**
     * 已发送
     */
    SEND(1, "已发送"),
    /**
     * 已删除
     */
    DELETE(2, "已拒收"),
    /**
     * 已拒收
     */
    REJECT(3, "未支付");
    @EnumValue
    private Integer id;
    @JsonValue
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    LetterState(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}