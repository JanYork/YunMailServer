/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.security;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import net.totime.mail.entity.Perm;
import net.totime.mail.entity.Role;
import net.totime.mail.entity.UserToPrem;
import net.totime.mail.entity.UserToRole;
import net.totime.mail.enums.AdminRole;
import net.totime.mail.service.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/26
 * @description 权限校验接口实现类
 * @see StpInterface
 * @since 1.0.0
 */
@Configuration
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private RoleService role;
    @Resource
    private PermService perm;
    @Resource
    private UserToRoleService userToRole;
    @Resource
    private UserToPremService userToPrem;
    @Resource
    private AdminService adminService;
    private static final String ADMIN = "admin";

    /**
     * 一个账号所拥有的权限码集合
     *
     * @param loginId   登录id
     * @param loginType 登录类型
     * @return {@link List}<{@link String}> 权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Long id = Long.valueOf(loginId.toString());
        if (ADMIN.equals(loginType)) {
            return null;
        }
        List<String> list = userToPrem.listObjs(
                new LambdaQueryWrapper<UserToPrem>()
                        .eq(UserToPrem::getUserId, id)
                        .select(UserToPrem::getPremId),
                Object::toString
        );
        return perm.listObjs(
                new LambdaQueryWrapper<Perm>()
                        .in(Perm::getId, list)
                        .select(Perm::getPermissions),
                Object::toString
        );
    }

    /**
     * 一个账号所拥有的角色标识集合
     *
     * @param loginId   登录id
     * @param loginType 登录类型
     * @return {@link List}<{@link String}> 角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long id = Long.valueOf(loginId.toString());
        if (ADMIN.equals(loginType)) {
            String userRole = AdminRole.getNameByCode(adminService.getById(id).getRole());
            if (userRole != null) {
                return List.of(userRole);
            }
        }
        List<String> list = userToRole.listObjs(
                new LambdaQueryWrapper<UserToRole>()
                        .eq(UserToRole::getUserId, id)
                        .select(UserToRole::getRoleId),
                Object::toString
        );
        return role.listObjs(
                new LambdaQueryWrapper<Role>()
                        .in(Role::getId, list)
                        .select(Role::getName),
                Object::toString
        );
    }
}
