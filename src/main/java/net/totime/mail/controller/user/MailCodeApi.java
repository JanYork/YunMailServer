/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.enums.KeyType;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.UserService;
import net.totime.mail.util.MailVerifyUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/19
 * @description 邮件验证码
 * @since 1.0.0
 */
@RestController
@Api(tags = "邮件验证码(需登录)")
@RequestMapping("/user/mail/code")
public class MailCodeApi {
    @Resource
    private MailVerifyUtil mailVerifyUtil;

    @Resource
    private UserService userService;

    /**
     * 获取邮件验证码
     *
     * @param mail 邮箱
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/get")
    @ApiOperation("获取邮件验证码")
    public ApiResponse<Boolean> getMailCode(String mail) {
        return mailVerifyUtil.code(mail, KeyType.NORMAL, 120L);
    }

    /**
     * 获取邮件验证码
     *
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/getByUser")
    @ApiOperation("获取邮件验证码")
    public ApiResponse<Boolean> getMailCodeByUser() {
        String mail = userService.getById(StpUtil.getLoginIdAsLong()).getEmail();
        if (mail == null) {
            throw new GloballyUniversalException(666, "请先绑定邮箱");
        }
        return mailVerifyUtil.code(mail, KeyType.NORMAL, 120L);
    }
}
