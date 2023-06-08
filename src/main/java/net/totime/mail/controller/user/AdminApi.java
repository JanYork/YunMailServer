/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import net.totime.mail.annotation.SaAdminCheckLogin;
import net.totime.mail.annotation.SaAdminCheckRole;
import net.totime.mail.entity.Admin;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.security.StpAdminUtil;
import net.totime.mail.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Admin)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 管理员控制器
 * @since 2023-03-26 17:54:04
 */
@RestController
@RequestMapping("/admin")
public class AdminApi {
    @Resource
    private AdminService adminService;

    /**
     * 获取用户信息
     */
    @GetMapping("/getInfo")
    @SaAdminCheckLogin
    public ApiResponse<Admin> getAdminInfo() {
        return ApiResponse.ok(adminService.getById(StpAdminUtil.getLoginIdAsLong()));
    }

    /**
     * 获取管理员列表
     *
     * @return {@link ApiResponse}<{@link List}<{@link Admin}>>
     */
    @GetMapping("/list")
    @SaAdminCheckLogin
    public ApiResponse<List<Admin>> getAdminList() {
        return ApiResponse.ok(adminService.list());
    }

    /**
     * 删除管理员
     * @param id 管理员id
     * @return {@link ApiResponse}
     */
    @PostMapping("/delete")
    @SaAdminCheckRole("super")
    public ApiResponse<Boolean> deleteAdmin(Long id) {
        return ApiResponse.ok(adminService.removeById(id));
    }

    /**
     * 添加管理员
     * @param admin 管理员信息
     * @return {@link ApiResponse}
     */
    @PostMapping("/insert")
    @SaAdminCheckRole("super")
    public ApiResponse<Boolean> addAdmin(Admin admin) {
        return ApiResponse.ok(adminService.save(admin));
    }

    /**
     * 修改管理员
     * @param admin 管理员信息
     * @return {@link ApiResponse}
     */
    @PostMapping("/update")
    @SaAdminCheckRole("super")
    public ApiResponse<Boolean> updateAdmin(Admin admin) {
        return ApiResponse.ok(adminService.updateById(admin));
    }
}
