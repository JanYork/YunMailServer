/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/11
 * @description 快递助手订阅返回数据
 * @since 1.0.0
 */
@Data
@Builder
public class CourierSubVO {
    private String traceId;
    private String isSub;
}
