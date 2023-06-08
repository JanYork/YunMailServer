/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/19
 * @description 支付工具类
 * @since 1.0.0
 */
public class PayUtils {
    /**
     * 分转元，保留两位小数
     *
     * @param i 分
     * @return {@link BigDecimal}
     */
    public static BigDecimal intToBigDecimal(Integer i) {
        return BigDecimal.valueOf(i).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /**
     * 分转元，保留两位小数
     *
     * @param i 分
     * @return {@link BigDecimal}
     */
    public static BigDecimal intToBigDecimal(BigDecimal i) {
        return i.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /**
     * 元转分
     *
     * @param d 元
     * @return {@link Integer}
     */
    public static Integer bigDecimalToInt(BigDecimal d) {
        return d.multiply(BigDecimal.valueOf(100)).intValue();
    }

    /**
     * 小数点后移N位
     *
     * @param d 元
     * @param n 移动位数 不填默认2位
     * @return {@link BigDecimal}
     */
    public static BigDecimal movePointToLeft(BigDecimal d, Integer n) {
        if (n == null) {
            n = 2;
        }
        return d.movePointLeft(n);
    }
}
