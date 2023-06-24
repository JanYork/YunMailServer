/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.entity.User;
import net.totime.mail.enums.KeyType;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.UserService;
import net.totime.mail.util.BcryptUtil;
import net.totime.mail.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 云寄用户表(User)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-06-14 22:59:40
 */
@RestController
@RequestMapping("/user")
@Api(tags = "[用户]云寄用户信息接口")
public class UserApi {
    @Resource
    private UserService userService;
    @Resource
    private RedisUtil rut;

    /**
     * 验证改绑手机号验证码
     */
    @PostMapping("/check/bind/phone/code")
    @ApiOperation(value = "验证改绑手机号验证码")
    public ApiResponse<Boolean> checkBindPhoneCode(
            @RequestParam @Valid @NotNull(message = "验证码为空") String code) {
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        String key = KeyType.CHANGE_PHONE.getKey() + user.getPhone();
        String cacheCode = (String) rut.get(key);
        if (StringUtils.isEmpty(cacheCode)) {
            return ApiResponse.fail(false).message("验证码已过期");
        }
        if (!code.equals(cacheCode)) {
            return ApiResponse.fail(false).message("验证码错误");
        }
        rut.delete(key);
        // 缓存验证通过标识
        rut.set(KeyType.CHANGE_PHONE_FLAG.getKey() + user.getId(), true, 600L);
        return ApiResponse.ok(true).message("验证通过");
    }

    /**
     * 绑定手机号
     *
     * @param phone 手机号
     * @param code  验证码
     * @return {@link ApiResponse}<{@link String}> 绑定结果
     */
    @PostMapping("/bind/phone")
    @ApiOperation(value = "绑定手机号")
    public ApiResponse<Boolean> bindPhone(
            @RequestParam @Valid @NotNull(message = "手机号为空") String phone,
            @RequestParam @Valid @NotNull(message = "验证码为空") String code) {
        if (!isPhone(phone)) {
            return ApiResponse.fail(false).message("手机号格式错误");
        }
        if (existPhone(phone)) {
            return ApiResponse.fail(false).message("手机号已绑定其他账户");
        }
        String key = KeyType.BIND_PHONE.getKey() + phone;
        String cacheCode = (String) rut.get(key);
        if (StringUtils.isEmpty(cacheCode)) {
            return ApiResponse.fail(false).message("验证码已过期");
        }
        if (!code.equals(cacheCode)) {
            return ApiResponse.fail(false).message("验证码错误");
        }
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        String msg;
        if (user.getPhone() != null) {
            // 获取改绑手机号验证通过标识
            Boolean flag = (Boolean) rut.get(KeyType.CHANGE_PHONE_FLAG.getKey() + user.getId());
            if (ObjectUtils.isEmpty(flag)) {
                return ApiResponse.fail(false).message("请先验证原手机号");
            }
            rut.delete(KeyType.CHANGE_PHONE_FLAG.getKey() + user.getId());
            msg = "改绑成功";
        } else {
            msg = "绑定成功";
        }
        user.setPhone(phone);
        if (!userService.updateById(user)) {
            return ApiResponse.fail(false).message("系统错误");
        }
        rut.delete(key);
        return ApiResponse.ok(true).message(msg);
    }

    /**
     * 绑定邮箱
     *
     * @param email 邮箱
     * @param code  验证码
     * @return {@link ApiResponse}<{@link String}> 绑定结果
     */
    @PostMapping("/bind/email")
    @ApiOperation(value = "绑定邮箱")
    public ApiResponse<Boolean> bindEmail(
            @RequestParam @Valid @NotNull(message = "邮箱为空") String email,
            @RequestParam @Valid @NotNull(message = "验证码为空") String code) {
        if (!isEmail(email)) {
            return ApiResponse.fail(false).message("邮箱格式错误");
        }
        if (existEmail(email)) {
            return ApiResponse.fail(false).message("邮箱已绑定其他账户");
        }
        String key = KeyType.OPEN_EMAIL.getKey() + email;
        String cacheCode = (String) rut.get(key);
        if (StringUtils.isEmpty(cacheCode)) {
            return ApiResponse.fail(false).message("验证码已过期");
        }
        if (!code.equals(cacheCode)) {
            return ApiResponse.fail(false).message("验证码错误");
        }
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        String msg;
        if (user.getEmail() != null) {
            // TODO：通知模板(P1)
            msg = "改绑成功";
        } else {
            msg = "绑定成功";
        }
        user.setEmail(email);
        if (!userService.updateById(user)) {
            return ApiResponse.fail(false).message("系统错误");
        }
        rut.delete(key);
        return ApiResponse.ok(true).message(msg);
    }

    /**
     * 重置密码
     *
     * @param phone 电话
     * @param code  验证码
     * @return {@link ApiResponse}<{@link String}>
     */
    @RequestMapping("/reset")
    @ApiOperation(value = "重置云寄账户密码", notes = "重置强依赖于手机号")
    public ApiResponse<String> reset(
            @RequestParam @Valid @NotNull(message = "手机号为空") String phone,
            @RequestParam @Valid @NotNull(message = "验证码为空") String code,
            @RequestParam @Valid @NotNull(message = "密码为空") String pwd
    ) {
        if (!isPhone(phone)) {
            return ApiResponse.fail("手机号格式错误");
        }
        if (pwd.length() < 6 || pwd.length() > 16) {
            return ApiResponse.fail("密码长度为6-16个字符");
        }
        User user = userService.getOne(
                new LambdaQueryWrapper<User>().eq(User::getPhone, phone)
        );
        if (ObjectUtils.isEmpty(user)) {
            return ApiResponse.fail("账户不存在");
        }
        String key = KeyType.CHANGE_PASSWORD.getKey() + phone;
        String cacheCode = (String) rut.get(key);
        if (StringUtils.isEmpty(cacheCode)) {
            return ApiResponse.fail("验证码已过期");
        }
        if (!code.equals(cacheCode)) {
            return ApiResponse.fail("验证码错误");
        }
        user.setPwd(BcryptUtil.encrypt(pwd));
        if (!userService.updateById(user)) {
            return ApiResponse.fail("重置失败").message("系统错误");
        }
        rut.delete(key);
        return ApiResponse.ok("重置成功");
    }

    /**
     * 判断手机号是否存在
     *
     * @param phone 电话
     * @return {@link Boolean}
     */
    private Boolean existPhone(String phone) {
        User user = userService.getOne(
                new LambdaQueryWrapper<User>().eq(User::getPhone, phone)
        );
        return !ObjectUtils.isEmpty(user);
    }

    /**
     * 判断用户是否存在
     *
     * @param email 邮箱
     * @return {@link Boolean}
     */
    private Boolean existEmail(String email) {
        User user = userService.getOne(
                new LambdaQueryWrapper<User>().eq(User::getEmail, email)
        );
        return !ObjectUtils.isEmpty(user);
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email 邮箱
     * @return {@link Boolean}
     */
    private Boolean isEmail(String email) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(regex);
    }

    /**
     * 判断手机号是否合法
     *
     * @param phone 手机号
     * @return {@link Boolean}
     */
    private Boolean isPhone(String phone) {
        String regex = "^1[3-9]\\d{9}$";
        return phone.matches(regex);
    }
}
