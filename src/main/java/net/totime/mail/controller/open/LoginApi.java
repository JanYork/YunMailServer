/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.open;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.dto.UserRegisterDTO;
import net.totime.mail.entity.User;
import net.totime.mail.enums.KeyType;
import net.totime.mail.enums.UserState;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.UserService;
import net.totime.mail.util.BcryptUtil;
import net.totime.mail.util.RedisUtil;
import net.totime.mail.util.SnowflakeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/26
 * @description 登录相关接口
 * @since 1.0.0
 */
@RestController
@Api(tags = "[开放]云寄注册与登录接口")
@Validated
@RequestMapping("/api/v1")
public class LoginApi {
    @Resource
    private UserService userService;
    @Resource
    private MapperFacade mapperFacade;
    @Resource
    private RedisUtil rut;

    /**
     * 云寄登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link ApiResponse}<{@link String}> 登录结果
     */
    @PostMapping("/login")
    @SaIgnore
    @ApiOperation(value = "登录云寄账户", notes = "默认密码和用户名登录，账户名可以是用户名、手机号、邮箱")
    public ApiResponse<String> login(
            @RequestParam @Valid @NotNull(message = "账户为空") String username,
            @RequestParam @Valid @NotNull(message = "密码为空") String password
    ) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        // 手机号正则
        String regexPhone = "^1[3-9]\\d{9}$";
        // 邮箱正则
        String regexEmail = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (username.matches(regexPhone)) {
            qw.eq(User::getPhone, username);
        } else if (username.matches(regexEmail)) {
            qw.eq(User::getEmail, username);
        } else {
            qw.eq(User::getName, username);
        }
        User user = userService.getOne(qw);
        if (ObjectUtils.isEmpty(user)) {
            return ApiResponse.fail("此账户不存在");
        }
        if (!BcryptUtil.verify(password, user.getPwd())) {
            return ApiResponse.fail("密码错误");
        }
        if (user.getState().equals(UserState.FROZEN.getCode())) {
            return ApiResponse.fail("账户已被禁用");
        }
        if (user.getState().equals(UserState.DELETED.getCode())) {
            return ApiResponse.fail("账户不存在");
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
    @SaIgnore
    @PostMapping("/login/phone")
    @ApiOperation(value = "手机号验证码登录云寄账户", notes = "手机号登录")
    public ApiResponse<String> phoneLogin(
            @RequestParam @Valid @NotNull(message = "手机号为空") String phone,
            @RequestParam @Valid @NotNull(message = "验证码为空") String code) {
        if (!isPhone(phone)) {
            return ApiResponse.fail("手机号格式错误");
        }
        String key = KeyType.OPEN_PHONE.getKey() + phone;
        String cacheCode = (String) rut.get(key);
        if (StringUtils.isEmpty(cacheCode)) {
            return ApiResponse.fail("验证码已过期");
        }
        if (!code.equals(cacheCode)) {
            return ApiResponse.fail("验证码错误");
        }
        User user = userService.getOne(
                new LambdaQueryWrapper<User>().eq(User::getPhone, phone)
        );
        // 未注册直接注册
        if (ObjectUtils.isEmpty(user)) {
            long snowflakeId = SnowflakeUtil.getSnowflakeId();
            User u = new User();
            u.setId(snowflakeId);
            u.setCreateTime(new Date());
            u.setPhone(phone);
            u.setName(phone);
            u.setNickName(getNickName(phone));
            u.setPwd(BcryptUtil.encrypt(phone + code + SnowflakeUtil.getSnowflakeId()));
            boolean save = userService.save(u);
            if (!save) {
                return ApiResponse.fail("登录失败").message("系统错误");
            }
            StpUtil.login(snowflakeId);
            String tokenValue = StpUtil.getTokenValue();
            assert tokenValue != null;
            return ApiResponse.ok(tokenValue);
        }
        // 已注册直接登录
        if (user.getState().equals(UserState.DELETED.getCode())) {
            return ApiResponse.fail("账户不存在");
        }
        if (user.getState().equals(UserState.FROZEN.getCode())) {
            return ApiResponse.fail("账户已被禁用");
        }
        rut.delete(key);
        StpUtil.login(user.getId());
        String tokenValue = StpUtil.getTokenValue();
        assert tokenValue != null;
        return ApiResponse.ok(tokenValue);
    }

    /**
     * 注册云寄账户
     *
     * @param userRegisterDTO 用户信息
     * @return {@link ApiResponse}<{@link String}> 注册结果
     */
    @PostMapping("/register")
    @SaIgnore
    @ApiOperation(value = "注册云寄账户", notes = "[强制]绑定手机号")
    public ApiResponse<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        if (existName(userRegisterDTO.getNickName())) {
            return ApiResponse.fail("账户名已存在");
        }
        if (existPhone(userRegisterDTO.getPhone())) {
            return ApiResponse.fail("手机号已存在");
        }
        // 验证码校验
        String key = KeyType.OPEN_PHONE.getKey() + userRegisterDTO.getPhone();
        String cacheCode = (String) rut.get(key);
        if (StringUtils.isEmpty(cacheCode)) {
            return ApiResponse.fail("验证码已过期");
        }
        if (!userRegisterDTO.getCode().equals(cacheCode)) {
            return ApiResponse.fail("验证码错误");
        }
        User user = mapperFacade.map(userRegisterDTO, User.class);
        user.setId(SnowflakeUtil.getSnowflakeId());
        user.setCreateTime(new Date());
        user.setPwd(BcryptUtil.encrypt(userRegisterDTO.getPwd()));
        if (!userService.save(user)) {
            return ApiResponse.fail("注册失败").message("系统错误");
        }
        rut.delete(key);
        StpUtil.login(user.getId());
        String tokenValue = StpUtil.getTokenValue();
        assert tokenValue != null;
        return ApiResponse.ok(tokenValue).message("注册成功");
    }


    /**
     * 退出登录
     *
     * @return {@link ApiResponse}<{@link String}> 退出结果
     */
    @RequestMapping("/logout")
    @SaCheckLogin
    @ApiOperation(value = "退出云寄账户", notes = "退出登录,前提是已登录")
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
                new LambdaQueryWrapper<User>().eq(User::getName, name)
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
