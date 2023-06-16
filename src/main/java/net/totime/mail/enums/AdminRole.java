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
 * @description 管理员角色枚举
 * @see Enum
 * @since 1.0.0
 */
public enum AdminRole {
    /**
     * 超级管理员
     */
    SUPER_ADMIN(0, "super_admin"),
    /**
     * 普通管理员
     */
    ADMIN(1, "admin"),
    /**
     * 运营人员
     */
    OPERATOR(2, "operator");
    private Integer code;
    private String name;

    AdminRole(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (AdminRole adminRole : AdminRole.values()) {
            if (adminRole.getCode().equals(code)) {
                return adminRole.getName();
            }
        }
        return null;
    }
}
