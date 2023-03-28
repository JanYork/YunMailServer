package com.totime.mail.util;

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

    /**
     * 密码加密加盐
     *
     * @param plainText 明文
     * @param salt      盐
     * @return {@link String} 加密后的密文
     * @since 1.0.1
     */
    public static String encrypt(String plainText, String salt) {
        return BCrypt.hashpw(plainText, salt);
    }

    /**
     * 密码校验加盐
     *
     * @param plainText  明文
     * @param cipherText 密文
     * @param salt       盐
     * @return {@link Boolean} true:校验成功 false:校验失败
     * @since 1.0.1
     */
    public static Boolean verify(String plainText, String cipherText, String salt) {
        return BCrypt.checkpw(plainText, encrypt(plainText, salt));
    }

    /**
     * 生成盐
     *
     * @return {@link String} 盐
     * @since 1.0.1
     */
    public static String gensalt() {
        return BCrypt.gensalt();
    }

    /**
     * 生成盐
     *
     * @param logRounds 日志轮数
     * @return {@link String} 盐
     * @since 1.0.1
     */
    public static String gensalt(int logRounds) {
        return BCrypt.gensalt(logRounds);
    }
}