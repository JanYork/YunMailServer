package cn.totime.config.tencent.sms;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JanYork
 * @date 2023/1/12 14:09
 * @description 腾讯云短信配置类
 */
@Configuration
public class SmsConfig {
    /**
     * 腾讯云secretId
     */
    @Value("${sms.secret-id}")
    private String secretId;

    /**
     * 腾讯云secretKey
     */
    @Value("${sms.secret-key}")
    private String secretKey;

    /**
     * 实例化腾讯云认证对象
     *
     * @return 腾讯云认证对象
     */
    @Bean
    public Credential getCredential() {
        return new Credential(secretId, secretKey);
    }

    /**
     * 实例化腾讯云http请求对象
     *
     * @return 腾讯云http请求对象
     */
    @Bean
    public HttpProfile getHttpProfile() {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        return httpProfile;
    }

    /**
     * 实例化客户端配置对象
     *
     * @return 客户端配置对象
     */
    @Bean
    public ClientProfile getClientProfile() {
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(getHttpProfile());
        return clientProfile;
    }

    /**
     * 实例化腾讯云短信服务的client对象
     *
     * @return 腾讯云短信服务的client对象
     */
    @Bean
    public SmsClient getSmsClient() {
        return new SmsClient(getCredential(), "ap-guangzhou", getClientProfile());
    }
}