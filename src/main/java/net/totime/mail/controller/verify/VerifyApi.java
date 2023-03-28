package net.totime.mail.controller.verify;

import net.totime.mail.domain.open.VerifyService;
import net.totime.mail.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/27
 * @description 多种验证接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/verify")
public class VerifyApi {
    @Resource
    private VerifyService verify;

    @RequestMapping("/code")
    public ApiResponse<String> getCode(@RequestBody String phone) {
        verify.cacheCode(phone);
        return ApiResponse.ok("获取成功，请在有限期内使用。").message("获取成功");
    }
}