package net.totime.mail.domain.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 阿里云短信服务
 * @since 1.0.0
 */
@Service
public class AliSmsService {
    @Value("${alibaba.cloud.sms.sign-name}")
    private String signName;
}