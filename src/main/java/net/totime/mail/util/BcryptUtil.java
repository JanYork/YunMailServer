/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/24
 * @description Bcrypt密码加密工具类
 * @since 1.0.0
 */
public class BcryptUtil {
    /**
     * 密码加密
     *
     * @param plainText 明文
     * @return {@link String} 加密后的密文
     */
    public static String encrypt(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    /**
     * 密码校验
     *
     * @param plainText  明文
     * @param cipherText 密文
     * @return {@link Boolean} true:校验成功 false:校验失败
     */
    public static Boolean verify(String plainText, String cipherText) {
        return BCrypt.checkpw(plainText, cipherText);
    }
}
