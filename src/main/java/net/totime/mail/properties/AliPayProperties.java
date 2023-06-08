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
 * @description 支付宝支付配置映射
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "pay.ali")
@Data
public class AliPayProperties {
    private String appId;
    private String pid;
    private String seller;
    private String inputCharset;
    private String notifyUrl;
    private String returnUrl;
    private String keyPublicPath;
    private String keyPrivatePath;
}
