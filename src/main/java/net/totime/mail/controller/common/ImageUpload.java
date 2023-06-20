/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.common;

import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.storage.tencent.service.TencentUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/03
 * @description 图片上传接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/img")
@Api(tags = "[公共]图片上传接口")
public class ImageUpload {
    @Resource
    private TencentUpload tud;

    /**
     * 上传图片
     *
     * @param file 文件
     * @return {@link ApiResponse}<{@link String}> 返回上传成功的URL
     */
    @SneakyThrows
    @RequestMapping("/upload/single")
    public ApiResponse<String> uploadImage(MultipartFile file) {
        return ApiResponse.ok(tud.upload(file));
    }

    /**
     * 上传图片
     *
     * @param url 文件链接
     * @return {@link ApiResponse}<{@link String}> 返回上传成功的URL
     */
    @SneakyThrows
    @RequestMapping("/upload/url")
    public ApiResponse<String> uploadImage(String url) {
        return ApiResponse.ok(tud.upload(url));
    }
}
