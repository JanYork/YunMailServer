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