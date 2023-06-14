/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.back.verify;

import com.baomidou.kaptcha.Kaptcha;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.annotation.RateLimiter;
import net.totime.mail.response.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/24 12点31分
 * @description 图像人机验证码接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/open/kaptcha")
@Api(tags = "人机图形验证接口")
@ApiSupport(author = "JanYork")
public class KaptchaApi {
    @Resource
    private Kaptcha kaptcha;
    /**
     * 有效时长
     */
    private static final int TIMEOUT = 60;

    /**
     * 验证码生成
     *
     * @return {@link ApiResponse}<{@link Boolean}>  true:生成成功 false:生成失败
     */
    @GetMapping("/render")
    @RateLimiter(count = 20)
    @ApiOperation(value = "获取图形验证码", notes = "此接口限制每个IP1分钟调用20次，注意：此接口为图形验证码，并且不具备完全的安全策略体系，如需高安全校验建议调用第三方服务，如：腾讯天御、极验")
    public ApiResponse<Boolean> render() {
        return ApiResponse.ok(StringUtils.isNotBlank(kaptcha.render()));
    }

    /**
     * 永久有效验证(默认900秒)
     *
     * @param code 验证码
     * @return {@link ApiResponse}<{@link Boolean}> true:验证成功 false:验证失败
     */
    @PostMapping("/valid")
    @RateLimiter(count = 20)
    @ApiOperation(value = "校验验证码", notes = "此接口限制每个IP1分钟调用20次，默认过期时间900秒，理论时间不过期")
    public ApiResponse<Boolean> validDefaultTime(@RequestParam String code) {
        //default timeout 900 seconds
        return ApiResponse.ok(kaptcha.validate(code));
    }

    /**
     * 有效时长验证
     *
     * @param code 验证码
     * @return {@link ApiResponse}<{@link Boolean}> true:验证成功 false:验证失败
     */
    @PostMapping("/validTime")
    @RateLimiter(count = 20)
    @ApiOperation(value = "校验验证码", notes = "此接口限制每个IP1分钟调用20次，过期时间60秒")
    public ApiResponse<Boolean> validCustomTime(@RequestParam String code) {
        return ApiResponse.ok(kaptcha.validate(code, TIMEOUT));
    }
}
