/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.back.user;

import cn.dev33.satoken.annotation.SaMode;
import net.totime.mail.annotation.SaAdminCheckRole;
import net.totime.mail.dto.UserDTO;
import net.totime.mail.entity.back.User;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/21
 * @description 用户控制器
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
public class UserApi {
    @Resource
    private UserService userService;

    /**
     * 新增用户
     *
     * @param userDTO 用户dto
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/insert")
    public ApiResponse<Boolean> insert(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return ApiResponse.ok(userService.save(user));
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @SaAdminCheckRole("super")
    public ApiResponse<Boolean> delete(Long id) {
        return ApiResponse.ok(userService.removeById(id));
    }

    /**
     * 更新用户
     *
     * @param userDTO 用户dto
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    public ApiResponse<Boolean> update(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return ApiResponse.ok(userService.updateById(user));
    }

    /**
     * 获取用户列表
     * @param page 页码
     * @param size 每页数量
     * @return {@link ApiResponse}<{@link List}<{@link User}>>
     */
    @GetMapping("/list")
    @SaAdminCheckRole(value = {"super", "admin"}, mode = SaMode.OR)
    public ApiResponse<List<User>> list(Integer page, Integer size) {
        return ApiResponse.ok(userService.list());
    }

    /**
     * 获取用户信息
     * @param id 用户id
     * @return {@link ApiResponse}<{@link User}>
     */
    @GetMapping("/getInfo")
    public ApiResponse<User> getInfo(Long id) {
        return ApiResponse.ok(userService.getById(id));
    }
}
