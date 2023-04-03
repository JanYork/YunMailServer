package net.totime.mail.storage.tencent.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/03
 * @description 图片处理工具类
 * @since 1.0.0
 */
public class ImageUtils {
    /**
     * 判断名称是否包含中文字符
     *
     * @param str 名称
     * @return boolean 是否包含中文字符
     */
    public static boolean isContainChinese(String str) {
        final String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 去除名称中的空格、回车、换行符、制表符
     *
     * @param str 名称
     * @return {@link String} 去除后的名称
     */
    public static String replaceBlank(String str) {
        if (str != null) {
            final String regex = "\\s*|\t|\r|\n";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    /**
     * 获取随机名称
     *
     * @param fileName 文件名
     * @return {@link String} 随机名称
     */
    public static String getRandomName(String fileName) {
        if (isContainChinese(fileName)) {
            return System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
        }
        String name = replaceBlank(fileName.substring(0, fileName.lastIndexOf(".")));
        return name + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
    }
}