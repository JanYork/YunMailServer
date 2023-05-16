package net.totime.mail.util;

import cn.hutool.crypto.digest.MD5;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/10
 * @description 快递助手工具类
 * @since 1.0.0
 */
@Deprecated
public class CourierUtil {
    /**
     * 签名
     *
     * @param params     参数个数
     * @param secret     密钥
     * @return {@link String}
     */
    public static String signTopRequest(Map<String, String> params, String secret) {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.hasText(key) && StringUtils.hasText(value)) {
                query.append(key).append(value);
            }
        }
        query.append(secret);
        byte[] bytes = MD5.create().digest(query.toString(), StandardCharsets.UTF_8);
        return byte2hex(bytes);
    }

    /**
     * 二进制转化为大写的十六进制
     *
     * @param bytes 字节
     * @return {@link String}
     */
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for(byte bt : bytes){
            String hex = Integer.toHexString(bt & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
