package net.totime.mail.controller.upload;

import lombok.SneakyThrows;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.storage.tencent.service.TencentUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/03
 * @description 图片上传接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/image")
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
     * 批量上传图片
     *
     * @param files 文件
     * @return {@link ApiResponse}<{@link List}<{@link String}> 返回上传成功的URL
     */
    @RequestMapping("/upload/batch")
    public ApiResponse<List<String>> uploadImage(MultipartFile[] files) {
        return ApiResponse.ok(tud.batchUpload(files));
    }
}