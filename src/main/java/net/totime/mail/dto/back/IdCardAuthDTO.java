/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.dto.back;

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
