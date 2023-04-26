package net.totime.mail.dto;

import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/24
 * @description 后台登录
 * @since 1.0.0
 */
@Data
public class AdminLoginDTO {
    private String username;
    private String password;
}
