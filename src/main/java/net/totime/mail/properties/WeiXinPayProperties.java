/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/30
 * @description 微信支付配置映射
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "pay.wx")
@Data
public class WeiXinPayProperties {
    private String appId;
    private String mchId;
    private String apiKey;
    private String inputCharset;
    private String notifyUrl;
    private String returnUrl;
    private String apiClientKeyP12;
}
