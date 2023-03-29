package net.totime.mail.config;

import com.ijpay.wxpay.WxPayApiConfig;
import com.ijpay.wxpay.WxPayApiConfigKit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/29
 * @description 微信支付配置
 * @since 1.0.0
 */
@Configuration
public class WxPayConfigure {
    @Bean
    public WxPayApiConfig wxPayApiConfig() {
        WxPayApiConfig wxPayApiConfig = new WxPayApiConfig();
        WxPayApiConfigKit.putApiConfig(wxPayApiConfig);
        return wxPayApiConfig;
    }
}