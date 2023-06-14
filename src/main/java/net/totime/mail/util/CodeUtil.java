package net.totime.mail.util;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/13
 * @description 验证码工具类
 * @since 1.0.0
 */
public class CodeUtil {
    /**
     * 数字字典
     */
    private static final String[] CODES = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    /**
     * 小写字母字典
     */
    private static final String[] LOWER_CODES = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    /**
     * 大写字母字典
     */
    private static final String[] UPPER_CODES = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Z"};


    /**
     * 生成验证码
     *
     * @param length 长度
     * @return {@link String}
     */
    public static String generateCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(CODES[(int) (Math.random() * CODES.length)]);
        }
        return code.toString();
    }

    /**
     * 生成验证码-数字、字母混合
     *
     * @param length 长度
     * @return {@link String}
     */
    public static String generateCodeMix(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (Math.random() > 0.5) {
                code.append(CODES[(int) (Math.random() * CODES.length)]);
            } else {
                code.append(LOWER_CODES[(int) (Math.random() * LOWER_CODES.length)]);
            }
        }
        return code.toString();
    }

    /**
     * 生成验证码-数字、字母各一半混合
     *
     * @param length 长度
     * @return {@link String}
     */
    public static String generateCodeMixHalf(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length / 2; i++) {
            code.append(CODES[(int) (Math.random() * CODES.length)]);
            code.append(UPPER_CODES[(int) (Math.random() * UPPER_CODES.length)]);
        }
        return code.toString();
    }

    /**
     * 判断两个字符串是否相等，大小写敏感
     *
     * @param a 值1
     * @param b 值2
     * @return boolean 是否相等
     */
    public static boolean equals(String a, String b) {
        return a.equals(b);
    }

    /**
     * 判断两个字符串是否相等，大小写不敏感
     *
     * @param a 值1
     * @param b 值2
     * @return boolean 是否相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return a.equalsIgnoreCase(b);
    }
}
