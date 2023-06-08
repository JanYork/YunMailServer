/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JanYork
 * @date 2023/3/8 14:57
 * @description IP工具类
 */
public class IpUtils {
    public static final String[] HEADER_NAMES = {
            "x-forwarded-for",
            "Proxy-Client-IP",
            "X-Forwarded-For",
            "WL-Proxy-Client-IP",
            "X-Real-IP"
    };

    /**
     * 获取IP
     *
     * @param request 请求
     * @return IP
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        String ip = null;
        for (String header : HEADER_NAMES) {
            ip = request.getHeader(header);
            if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                break;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /* -------------------------------------------------------------------------------------------------------- */

    private static final byte SECTION_1 = 0x0A; // 10.x.x.x/8
    private static final byte SECTION_2 = (byte) 0xAC; // 172.16.x.x/12
    private static final byte SECTION_3 = (byte) 0x10;
    private static final byte SECTION_4 = (byte) 0x1F;
    private static final byte SECTION_5 = (byte) 0xC0; // 192.168.x.x/16
    private static final byte SECTION_6 = (byte) 0xA8;

    /**
     * 判断是否为内网IP
     *
     * @param addr 地址
     * @return 结果
     */
    private static boolean internalIp(byte[] addr) {
        if (addr == null || addr.length < 2) {
            return true;
        }

        final byte b0 = addr[0];
        final byte b1 = addr[1];

        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_5:
                return b1 == SECTION_6;
            case SECTION_2:
            default:
                return false;
        }
    }

    /* -------------------------------------------------------------------------------------------------------- */

    public static final int IPV4_LENGTH = 4;
    public static final String IPV4_REGEX = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";

    /**
     * 将IPv4地址转换为字节数组
     *
     * @param text IPv4地址
     * @return 字节数组
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (text == null || text.length() == 0) {
            throw new IllegalArgumentException("Invalid IPv4 address: " + text);
        }

        if (!text.matches(IPV4_REGEX)) {
            throw new IllegalArgumentException("Invalid IPv4 address format: " + text);
        }

        byte[] bytes = new byte[IPV4_LENGTH];
        String[] elements = text.split("\\.");

        for (int i = 0; i < elements.length; i++) {
            int value = Integer.parseInt(elements[i]);

            if (value < 0 || value > 255) {
                throw new IllegalArgumentException("Invalid IPv4 address: " + text);
            }

            bytes[i] = (byte) value;
        }

        return bytes;
    }
}
