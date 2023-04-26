package net.totime.mail.vo;

import cn.dev33.satoken.stp.SaTokenInfo;
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
public class AuthVO {
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
    private SaTokenInfo token;
}
