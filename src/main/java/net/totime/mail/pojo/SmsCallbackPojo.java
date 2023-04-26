package net.totime.mail.pojo;

import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/12
 * @description 腾讯云短信回调接收
 * @since 1.0.0
 */
@Data
public class SmsCallbackPojo {
    private String extend;
    private String mobile;
    private String nationcode;
    private String sign;
    private String text;
    private Long time;
}
