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
        return "mail" + System.currentTimeMillis();
    }
}