/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import net.totime.mail.annotation.SaAdminCheckLogin;
import net.totime.mail.dto.AdminLoginDTO;
import net.totime.mail.entity.Admin;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.security.StpAdminUtil;
import net.totime.mail.service.AdminService;
import net.totime.mail.util.BcryptUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/24
 * @description 后台登录
 * @since 1.0.0
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "[后台]运营台登录")
public class AdminLogin {
    @Resource
    private AdminService adminService;

    /**
     * 后台登录接口
     *
     * @param login 登录信息
     * @return {@link ApiResponse} 登录结果
     */
    @PostMapping ("/login")
    public ApiResponse login(@RequestBody AdminLoginDTO login) {
        Admin admin = adminService.getOne(
                new QueryWrapper<Admin>().eq("name", login.getUsername())
        );
        Optional.ofNullable(admin).orElseThrow(() -> new RuntimeException("用户不存在"));
        if (!BcryptUtil.verify(login.getPassword(), admin.getPwd())) {
            throw new RuntimeException("密码错误");
        }
        StpAdminUtil.login(admin.getId());
        String tokenValue = StpAdminUtil.getTokenValue();
        assert tokenValue != null;
        return ApiResponse.ok(tokenValue);
    }

    /**
     * 后台登出接口
     *
     * @return {@link ApiResponse} 登出结果
     */
    @RequestMapping("/logout")
    @SaAdminCheckLogin
    public ApiResponse logout() {
        StpAdminUtil.logout();
        return ApiResponse.ok("登出成功");
    }

    /**
     * 注册后台用户
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @return {@link ApiResponse} 注册结果
     */
    @RequestMapping("/register")
    public ApiResponse register(String username, String password, String email) {
        Admin admin = new Admin();
        admin.setName(username);
        admin.setPwd(BcryptUtil.encrypt(password));
        admin.setMail(email);
        admin.setCreateTime(new Date());
        if (adminService.save(admin)){
            return ApiResponse.ok("注册成功");
        }
        return ApiResponse.fail("注册失败");
    }
}
