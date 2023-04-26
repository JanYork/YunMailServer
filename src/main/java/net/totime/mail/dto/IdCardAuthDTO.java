package net.totime.mail.dto;

import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/13
 * @description 实名认证要素
 * @since 1.0.0
 */
@Data
public class IdCardAuthDTO {
    private String name;
    private String idCard;
    private String phone;
}
