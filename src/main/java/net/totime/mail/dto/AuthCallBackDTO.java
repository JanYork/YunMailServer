/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.dto;

import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/02
 * @description 第三方登录回调
 * @since 1.0.0
 */
@Data
public class AuthCallBackDTO {
        private String uuid;
        private String nickname;
        private String avatar;
        private String email;
        private String remark;
        private String source;
        private TokenInfo token;

    @Data
    public static class TokenInfo {
        private String accessToken;
        private Integer expireIn;
        private String refreshToken;
        private Integer refreshTokenExpireIn;
        private String openId;
    }
}
