/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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
public enum MailState {
    /**
     * 未发送
     */
    NOT_SEND(0, "未发出"),
    /**
     * 已发送
     */
    SEND(1, "已发出"),
    /**
     * 已拒收
     */
    DELETE(2, "已拒收"),
    /**
     * 未支付
     */
    REJECT(3, "未支付"),
    /**
     * 已收取
     */
    RECEIVE(4, "已收取");
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

    MailState(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
