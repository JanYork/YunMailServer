package net.totime.mail.controller.verify;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.domain.open.VerifyService;
import net.totime.mail.response.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/27
 * @description 多种验证接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/verify")
@Api(tags = "云寄注册与登录接口")
@ApiSupport(author = "JanYork")
public class VerifyApi {
    @Resource
    private VerifyService verify;

    /**
     * 获取验证码
     *
     * @param phone 电话
     * @return {@link ApiResponse}<{@link String}> 验证码
     */
    @RequestMapping("/code")
    @ApiOperation(value = "获取验证码", notes = "需要携带手机号参数")
    public ApiResponse<String> getCode(@RequestBody @NotNull String phone) {
        String regex = "^1[1-9]\\d{9}$";
        if (!phone.matches(regex)) {
            return ApiResponse.fail("手机号格式错误");
        }
        verify.cacheCode(phone);
        return ApiResponse.ok("获取成功，请在有限期内使用。").message("获取成功");
    }

    /**
     * 获取验证码
     *
     * @return {@link ApiResponse}<{@link String}> 验证码
     */
    @RequestMapping("/code/u")
    @ApiOperation(value = "获取验证码", notes = "自动获取用户手机号参数")
    @SaCheckLogin
    public ApiResponse<String> getCode() {

        return ApiResponse.ok("获取成功，请在有限期内使用。").message("获取成功");
    }
}