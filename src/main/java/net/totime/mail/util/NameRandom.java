/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import java.util.UUID;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/24
 * @description 随机名称生成器
 * @since 1.0.0
 */
public class NameRandom {
    /**
     * 字母表
     */
    private static final String[] LETTERS = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i",
            "j", "k", "m", "n", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z"
    };

    /**
     * 随机名称
     *
     * @param prefix 前缀
     * @return 随机名称
     */
    public static String randomName(String prefix) {
        String time = String.valueOf(System.currentTimeMillis()).substring(6);
        String str = LETTERS[(int) (Math.random() * 24)];
        return prefix + str + UUID.randomUUID().toString().replace("-", "").substring(0, 6) + time;
    }
}
