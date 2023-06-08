/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 邮件id生成工具类
 * @since 1.0.0
 */
public class IdUtils {
    /**
     * 生成邮件id
     *
     * @return {@link String} 邮件id
     */
    public static String getMailId() {
        return "MAIL" + System.currentTimeMillis();
    }

    /**
     * 生成信件id
     *
     * @return {@link String}
     */
    public static String getLetterId() {
        return "LETTER" + System.currentTimeMillis();
    }
}
