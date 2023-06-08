/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/21
 * @description 二维码工具类
 * @since 1.0.0
 */
public class QrUtil {
    private static final QrConfig CONFIG = new QrConfig(200, 200);
    private static final String LOGO_PATH = "logo.png";

    /**
     * 获取二维码
     *
     * @param content 内容
     * @return {@link byte[]}
     */
    public static byte[] getByteArray(String content) {
        return QrCodeUtil.generatePng(content, CONFIG);
    }

    /**
     * 获取带logo的二维码
     *
     * @param content 内容
     * @return {@link byte[]}
     */
    public static byte[] getByteArrayWithLogo(String content) {
        QrConfig config = CONFIG.setImg(LOGO_PATH);
        config.setMargin(1);
        return QrCodeUtil.generatePng(content, config);
    }
}
