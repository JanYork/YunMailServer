package net.totime.mail.enums;

/**
 * @author JanYork
 * @date 2023/3/8 13:48
 * @description 限流类型
 */
public enum LimitType {
    /**
     * 默认
     */
    DEFAULT,
    /**
     * 自定义key
     */
    CUSTOMER,
    /**
     * 根据IP地址限流
     */
    IP;
}