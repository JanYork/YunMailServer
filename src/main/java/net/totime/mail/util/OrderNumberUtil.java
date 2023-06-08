/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import cn.hutool.core.util.RandomUtil;
import net.totime.mail.enums.PayState;
import net.totime.mail.enums.PayType;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/14
 * @description 订单号生成工具类
 * @since 1.0.0
 */
public class OrderNumberUtil {
    /**
     * 生成赞助订单号
     *
     * @return {@link Long}
     */
    public static Long createSponsorOrderNumber(PayType type) {
        return Long.parseLong(System.currentTimeMillis() + RandomUtil.randomNumbers(4) + type.getId());
    }
}
