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
 * @date 2023/06/24
 * @description 富文本字数统计工具类
 * @since 1.0.0
 */
public class RichTextWordCountUtil {
    /**
     * 统计文本的字数
     *
     * @param text 文本
     * @return 文本的字数
     * @throws Exception 异常
     */
    public static int count(String text) throws Exception {
        text = text.replaceAll("<[^>]*>", "");
        text = text.replaceAll("\\s*|\t|\r|\n", "");
        text = text.replaceAll("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", "");
        int chineseCount = getChineseCount(text);
        int englishCount = getEnglishCount(text);
        int numberCount = getNumberCount(text);
        int otherCount = getOtherCount(text);
        return chineseCount + englishCount / 4 + numberCount / 4 + otherCount / 2;
    }

    /**
     * 获取中文字符个数
     *
     * @param text 文本
     * @return 中文字符个数
     */
    private static int getChineseCount(String text) {
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        for (int i = 0; i < text.length(); i++) {
            String temp = text.substring(i, i + 1);
            if (temp.matches(regEx)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取英文字符个数
     *
     * @param text 文本
     * @return 英文字符个数
     */
    private static int getEnglishCount(String text) {
        int count = 0;
        String regEx = "[a-zA-Z]";
        for (int i = 0; i < text.length(); i++) {
            String temp = text.substring(i, i + 1);
            if (temp.matches(regEx)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取数字个数
     *
     * @param text 文本
     * @return 数字个数
     */
    private static int getNumberCount(String text) {
        int count = 0;
        String regEx = "[0-9]";
        for (int i = 0; i < text.length(); i++) {
            String temp = text.substring(i, i + 1);
            if (temp.matches(regEx)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取其他字符个数(常用符号类)
     *
     * @param text 文本
     * @return 其他字符个数
     */
    private static int getOtherCount(String text) {
        int count = 0;
        String regEx = "[^a-zA-Z0-9\\u4e00-\\u9fa5]";
        for (int i = 0; i < text.length(); i++) {
            String temp = text.substring(i, i + 1);
            if (temp.matches(regEx)) {
                count++;
            }
        }
        return count;
    }
}
