package net.totime.mail.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/09
 * @description 腾讯云短信配置类
 * @since 1.0.0
 */
@Configuration
public class TencentSmsConfigure {
    @Value("${tencent.sms.secret-id}")
    private String secretId;
    @Value("${tencent.sms.secret-key}")
    private String secretKey;
    @Value("${tencent.sms.region}")
    private String region;
    /**
     * 腾讯云短信接口地址
     */
    private static final String ENDPOINT = "sms.tencentcloudapi.com";

    /**
     * 构建腾讯云短信客户端
     *
     * @return {@link SmsClient} 腾讯云短信客户端
     */
    @Bean
    public SmsClient buildClient() {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint(ENDPOINT);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(new Credential(secretId, secretKey), region, clientProfile);
    }
}
