/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.pojo;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/10
 * @description 快递助手公共参数
 * @since 1.0.0
 */
@Deprecated
public class CourierCommonParam {
    /**
     * API接口名称
     */
    private String method;
    /**
     * API接口名称
     */
    private String appKey;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 签名算法
     */
    private String signMethod;
    /**
     * API接口名称
     */
    private String format;
    /**
     * API协议版本
     */
    private String version;
}
