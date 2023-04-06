package net.totime.mail.controller.login;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import net.totime.mail.domain.open.VerifyService;
import net.totime.mail.dto.UserDTO;
import net.totime.mail.entity.User;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.impl.UserServiceImpl;
import net.totime.mail.util.BcryptUtil;
import net.totime.mail.util.SnowflakeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
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
        if (!BcryptUtil.verify(password, user.getPwd())) {
            throw new RuntimeException("密码错误");
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
     * 重置密码
     * TODO: 2021/3/26 重置密码 未完成
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
}