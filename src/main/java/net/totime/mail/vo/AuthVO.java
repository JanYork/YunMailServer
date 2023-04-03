package net.totime.mail.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/02
 * @description 登录后回调信息
 * @since 1.0.0
 */
@Data
@Builder
public class AuthVO implements Serializable {
    private static final long serialVersionUID = 5057287920338443519L;
    /**
     * 登录结果码
     */
    private Integer code;

    /**
     * 登录结果信息
     */
    private String msg;

    /**
     * 登录Token
     */
    private String token;
}