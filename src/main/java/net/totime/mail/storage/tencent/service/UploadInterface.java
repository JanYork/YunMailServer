/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.storage.tencent.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/24
 * @description 图片上传服务接口
 * @since 1.0.0
 */
public interface UploadInterface {
    /**
     * 上传图片
     *
     * @param file 文件
     * @return 返回上传成功的结果
     * @throws Exception 异常
     */
    String upload(MultipartFile file) throws Exception;

    /**
     * 批量上传
     *
     * @param files 文件
     * @return {@link List}<{@link String}> 返回上传成功的URL
     * @throws IOException 异常
     */
    List<String> batchUpload(MultipartFile[] files) throws IOException;

    /**
     * 上传
     *
     * @param url 文件链接
     * @return {@link String}
     */
    String upload(String url) throws IOException;
}
