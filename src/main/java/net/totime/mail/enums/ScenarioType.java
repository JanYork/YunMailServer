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
 * @date 2023/06/17
 * @description 业务场景类型
 * @see Enum
 * @since 1.0.0
 */
public enum ScenarioType {
    /**
     * 赞助
     */
    SPONSOR(1, "赞助"),
    /**
     * 信件
     */
    LETTER(2, "信件"),
    /**
     * 短信
     */
    SMS(3, "短信");
    private final Integer id;
    private final String desc;

    ScenarioType(Integer id, String desc) {
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
