package net.totime.mail.controller.other;

import lombok.extern.slf4j.Slf4j;
import net.totime.mail.pojo.SmsCallbackPojo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/12
 * @description 腾讯云短信回调接收
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/sms")
@Slf4j
public class TencentSmsCallback {
    /**
     * 短信回调接口
     *
     * @param callback 回调
     * @return {@link String} 返回值
     */
    @RequestMapping("/callback")
    public HashMap<String, String> callback(@RequestBody SmsCallbackPojo callback) {
        if (Objects.isNull(callback)) {
            return new HashMap<>(16) {
                private static final long serialVersionUID = 1950161893463962134L;

                {
                    put("result", "1");
                    put("errmsg", "参数错误");
                }
            };
        }

        log.info("callback:{}", callback);
        return new HashMap<>(16) {
            private static final long serialVersionUID = 1950161893463962134L;

            {
                put("result", "0");
                put("errmsg", "OK");
            }
        };
    }
}
