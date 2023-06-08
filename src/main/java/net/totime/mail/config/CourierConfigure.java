/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.config;

import com.raycloud.open.sdk.api.KdzsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/11
 * @description 快递物流服务配置类
 * @since 1.0.0
 */
@Configuration
public class CourierConfigure {
    @Value("${kdzs.key}")
    private String appKey;
    @Value("${kdzs.secret}")
    private String appSecret;

    /**
     * 快递物流服务客户端
     *
     * @return {@link KdzsClient}
     */
    @Bean
    public KdzsClient kdzsClient() {
        return new KdzsClient(appKey, appSecret);
    }
}
