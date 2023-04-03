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