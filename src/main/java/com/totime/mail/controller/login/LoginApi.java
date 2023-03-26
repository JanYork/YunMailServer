package com.totime.mail.controller.login;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.totime.mail.entity.User;
import com.totime.mail.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/26
 * @description 登录相关接口
 * @since 1.0.0
 */
@RestController
public class LoginApi {
    @Resource
    private UserServiceImpl userService;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @RequestMapping("/login")
    public String login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", username);
        User user = userService.getOne(queryWrapper);
        if (user == null) {
            return "用户不存在";
        }
        if (!user.getPwd().equals(password)) {
            return "密码错误";
        }
        //登录
        StpUtil.login(user.getId());
        return "登录成功";
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 注册结果
     */
    @RequestMapping("/register")
    public String register(String username, String password) {
        return "注册成功";
    }

    @RequestMapping("/ltest")
    @SaCheckLogin
    public String test() {
        return "测试成功";
    }

    @RequestMapping("/open/test")
    @SaCheckPermission("open.tes")
    public String test1() {
        return "测试成功";
    }

    @RequestMapping("/test")
    @SaCheckRole("admi")
    public String test2() {
        return "测试成功";
    }
}