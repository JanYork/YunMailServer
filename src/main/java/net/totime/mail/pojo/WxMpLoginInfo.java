/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/24
 * @description 微信公众号登录状态信息
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxMpLoginInfo implements Serializable {
    private static final long serialVersionUID = 7220418640716217694L;
    /**
     * 登录状态
     */
    private int state;
    /**
     * token
     */
    private String token;
}
