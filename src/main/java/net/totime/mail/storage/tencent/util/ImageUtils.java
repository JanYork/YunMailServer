/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.storage.tencent.util;

import org.apache.http.entity.ContentType;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final Integer MAX_NAME_LENGTH = 8;

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
        if (name.length() > MAX_NAME_LENGTH) {
            name = name.substring(0, MAX_NAME_LENGTH);
        }
        return name + System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 从URL获得图像
     *
     * @param imgUrl 图片链接
     * @return {@link byte[]} 图片字节数组
     */
    public static byte[] getImageFromUrl(String imgUrl) throws IOException {
        InputStream input;
        URL url = new URL(imgUrl);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.connect();
        httpUrl.getInputStream();
        input = httpUrl.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int numBytesRead;
        while ((numBytesRead = input.read(buf)) != -1) {
            output.write(buf, 0, numBytesRead);
        }
        byte[] data = output.toByteArray();
        output.close();
        input.close();
        return data;
    }
}
