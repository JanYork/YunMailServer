/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.login;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.domain.open.VerifyService;
import net.totime.mail.dto.UserDTO;
import net.totime.mail.entity.User;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.impl.UserServiceImpl;
import net.totime.mail.util.BcryptUtil;
import net.totime.mail.util.SnowflakeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/26
 * @description 登录相关接口
 * @since 1.0.0
 */
@RestController
@Api(tags = "云寄注册与登录接口")
@ApiSupport(author = "JanYork")
public class LoginApi {
    @Resource
    private UserServiceImpl userService;

    @Resource
    private VerifyService verify;

    /**
     * 云寄登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link ApiResponse}<{@link String}> 登录结果
     */
    @RequestMapping("/login")
    @ApiOperation(value = "登录云寄账户", notes = "默认密码和用户名登录")
    public ApiResponse<String> login(String username, String password) {
        User user = userService.getOne(
                new QueryWrapper<User>().eq("name", username)
        );
        Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("用户不存在"));
        if (!BcryptUtil.verify(password, user.getPwd(), user.getSalt())) {
            throw new RuntimeException("密码错误");
        }
        if (user.getState() == 0) {
            return ApiResponse.fail("账户已被禁用");
        }
        StpUtil.login(user.getId());
        String tokenValue = StpUtil.getTokenValue();
        assert tokenValue != null;
        return ApiResponse.ok(tokenValue);
    }

    /**
     * 云寄手机号登录接口
     *
     * @param phone 手机号
     * @param code  验证码
     * @return {@link ApiResponse}<{@link String}> 登录结果
     */
    @RequestMapping("/login/phone")
    @ApiOperation(value = "登录云寄账户", notes = "手机号登录")
    public ApiResponse<String> phoneLogin(String phone, String code) {
        if (!verify.verifyCode(phone, code)) {
            return ApiResponse.fail("验证码错误");
        }
        User user = userService.getOne(
                new QueryWrapper<User>().eq("phone", phone)
        );
        if (ObjectUtils.isEmpty(user)) {
            User u = new User();
            u.setId(SnowflakeUtil.getSnowflakeId());
            u.setCreateTime(new Date());
            u.setPhone(phone);
            u.setName(phone);
            u.setNickName(getNickName(phone));
            boolean save = userService.save(u);
            if (!save) {
                return ApiResponse.fail("登录失败").message("系统错误");
            }
            StpUtil.login(user.getId());
            String tokenValue = StpUtil.getTokenValue();
            assert tokenValue != null;
            return ApiResponse.ok(tokenValue);
        }
        if (user.getState() == 0) {
            return ApiResponse.fail("账户已被禁用");
        }
        StpUtil.login(user.getId());
        String tokenValue = StpUtil.getTokenValue();
        assert tokenValue != null;
        return ApiResponse.ok(tokenValue);
    }

    /**
     * 注册云寄账户
     *
     * @param userDTO 用户信息
     * @return {@link ApiResponse}<{@link String}> 注册结果
     */
    @RequestMapping("/register")
    @ApiOperation(value = "注册云寄账户", notes = "注册强依赖于手机号")
    public ApiResponse<String> register(@Valid @RequestBody UserDTO userDTO) {
        if (!verify.verifyCode(userDTO.getPhone(), userDTO.getCode())) {
            return ApiResponse.fail("验证码错误");
        }
        if (existName(userDTO.getNickName())) {
            return ApiResponse.fail("账户名已存在");
        }
        if (existPhone(userDTO.getPhone())) {
            return ApiResponse.fail("手机号已存在");
        }
        String gensalt = BcryptUtil.gensalt();
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setId(SnowflakeUtil.getSnowflakeId());
        user.setCreateTime(new Date());
        user.setPwd(BcryptUtil.encrypt(userDTO.getPwd(), gensalt));
        user.setSalt(gensalt);
        if (!userService.save(user)) {
            return ApiResponse.fail("注册失败").message("系统错误");
        }
        StpUtil.login(user.getId());
        String tokenValue = StpUtil.getTokenValue();
        assert tokenValue != null;
        return ApiResponse.ok(tokenValue).message("注册成功");
    }

    /**
     * 绑定手机号
     *
     * @param phone 手机号
     * @param code  验证码
     * @return {@link ApiResponse}<{@link String}> 绑定结果
     */
    @RequestMapping("/bind/phone")
    @ApiOperation(value = "绑定手机号", notes = "绑定手机号")
    public ApiResponse<String> bindPhone(String phone, String code) {
        if (!verify.verifyCode(phone, code)) {
            return ApiResponse.fail("验证码错误");
        }
        if (existPhone(phone)) {
            return ApiResponse.fail("手机号已存在");
        }
        User user = userService.getById(Long.valueOf(StpUtil.getLoginIdAsString()));
        user.setPhone(phone);
        if (!userService.updateById(user)) {
            return ApiResponse.fail("绑定失败").message("系统错误");
        }
        return ApiResponse.ok("绑定成功");
    }

    /**
     * 重置密码
     *
     * @param userDTO 用户信息
     * @return {@link ApiResponse}<{@link String}> 重置结果
     */
    @RequestMapping("/reset")
    @ApiOperation(value = "重置云寄账户密码", notes = "重置强依赖于手机号")
    public ApiResponse<String> reset(@Valid @RequestBody UserDTO userDTO) {
        if (!verify.verifyCode(userDTO.getPhone(), userDTO.getCode())) {
            return ApiResponse.fail("验证码错误");
        }
        User user = userService.getOne(
                new QueryWrapper<User>().eq("phone", userDTO.getPhone())
        );
        Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("用户不存在"));
        String gensalt = BcryptUtil.gensalt();
        user.setPwd(BcryptUtil.encrypt(userDTO.getPwd(), gensalt));
        user.setSalt(gensalt);
        if (!userService.updateById(user)) {
            return ApiResponse.fail("重置失败").message("系统错误");
        }
        return ApiResponse.ok("重置成功");
    }

    /**
     * 退出登录
     *
     * @return {@link ApiResponse}<{@link String}> 退出结果
     */
    @RequestMapping("/logout")
    @SaCheckLogin
    @ApiOperation(value = "退出云寄账户", notes = "退出登录")
    public ApiResponse<Boolean> logout() {
        StpUtil.logout();
        return ApiResponse.ok(true);
    }

    /**
     * 获取昵称
     *
     * @param phone 电话
     * @return {@link String} 昵称
     */
    private String getNickName(String phone) {
        String letter = String.valueOf((char) (Math.random() * 26 + 65));
        String substring = phone.substring(phone.length() - 4);
        return "时空穿梭者:" + letter + substring;
    }

    /**
     * 判断用户是否存在
     *
     * @param name 用户名
     * @return {@link Boolean}
     */
    private Boolean existName(String name) {
        User user = userService.getOne(
                new QueryWrapper<User>().eq("name", name)
        );
        return !ObjectUtils.isEmpty(user);
    }

    /**
     * 判断用户是否存在
     *
     * @param phone 电话
     * @return {@link Boolean}
     */
    private Boolean existPhone(String phone) {
        User user = userService.getOne(
                new QueryWrapper<User>().eq("phone", phone)
        );
        return !ObjectUtils.isEmpty(user);
    }
}
