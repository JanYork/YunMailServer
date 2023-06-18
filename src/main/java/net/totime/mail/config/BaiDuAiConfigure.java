/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.config;

import com.baidu.aip.contentcensor.AipContentCensor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/18
 * @description 百度AI配置
 * @since 1.0.0
 */
@Configuration
public class BaiDuAiConfigure {
    @Value("${baidu.ai.app-id}")
    private String appId;
    @Value("${baidu.ai.api-key}")
    private String apiKey;
    @Value("${baidu.ai.secret-key}")
    private String secretKey;

    /**
     * 百度AI服务
     *
     * @return {@link AipContentCensor}
     */
    @Bean
    public AipContentCensor client() {
        AipContentCensor client = new AipContentCensor(appId, apiKey, secretKey);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }
}
