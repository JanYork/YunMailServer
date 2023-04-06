package net.totime.mail.storage.tencent.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.utils.Md5Utils;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.storage.tencent.util.ImageUtils;
import net.totime.mail.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/24
 * @description 腾讯云对象存储上传服务
 * @since 1.0.0
 */
@Service
public class TencentUpload implements UploadInterface {
    @Resource
    private COSClient cos;
    @Resource
    private RedisUtil rut;

    @Value("${tencent.cos.bucket}")
    private String bucketName;

    @Value("${tencent.cos.url}")
    private String url;
    /**
     * 支持的文件类型
     */
    private static final List<String> FILE_TYPE = Arrays.asList("image/png", "image/jpeg", "image/gif", "image/jpg");
    private static final Long CACHE_TIME = 3600L;

    /**
     * 上传图片
     *
     * @param file 文件
     * @return 返回上传成功的结果
     * @throws IOException 异常
     */
    @Override
    public String upload(MultipartFile file) throws IOException {
        return getFileUrl(file);
    }

    /**
     * 上传
     *
     * @param fileUrl 文件链接
     * @return {@link String}
     */
    @Override
    public String upload(String fileUrl) throws IOException {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String fileType = "image/" + fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!FILE_TYPE.contains(fileType)) {
            throw new GloballyUniversalException(500, "不支持的文件类型");
        }
        String md5 = Md5Utils.md5Hex(fileUrl);
        String url = (String)rut.get(md5);
        if (!StringUtils.isEmpty(url)) {
            return url;
        }
        String randomName = ImageUtils.getRandomName(fileName);
        //将图片转换为Byte数组
        byte[] bytes = ImageUtils.getImageFromUrl(fileUrl);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            cos.putObject(bucketName, randomName, inputStream, objectMetadata);
        } catch (Exception e) {
            throw new GloballyUniversalException(500, "上传失败");
        }
        url = this.url + "/" + randomName;
        rut.set(md5, url, CACHE_TIME);
        return url;
    }

    /**
     * 批量上传
     *
     * @param files 文件
     * @return {@link List}<{@link String}> 返回上传成功的URL
     */
    @Override
    public List<String> batchUpload(MultipartFile[] files) {
        return Arrays.stream(files).map(file -> {
            try {
                return getFileUrl(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private String getFileUrl(MultipartFile file) throws IOException {
        String fileType = file.getContentType();
        if (!FILE_TYPE.contains(fileType)) {
            throw new GloballyUniversalException(500, "文件类型不支持");
        }
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new GloballyUniversalException(500, "文件上传失败");
        }
        String md5 = Md5Utils.md5Hex(file.getBytes());
        String url = (String) rut.get(md5);
        if (!StringUtils.isEmpty(url)) {
            return url;
        }
        String fileName = ImageUtils.getRandomName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(fileType);
        try {
            cos.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
        } catch (Exception e) {
            throw new GloballyUniversalException(500, "文件上传失败");
        }
        url = this.url + "/" + fileName;
        rut.set(md5, url, CACHE_TIME);
        return url;
    }
}