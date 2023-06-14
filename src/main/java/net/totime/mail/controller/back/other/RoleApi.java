/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.back.other;

import net.totime.mail.entity.back.Role;
import net.totime.mail.service.RoleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Role)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-03-26 17:54:01
 */
@RestController
@RequestMapping("/role")
public class RoleApi {
    @Resource
    private RoleService rs;

    /**
     * 查询所有角色
     *
     * @return 所有角色
     */
    @RequestMapping("/all")
    public List<Role> all() {
        return rs.list();
    }

    /**
     * 根据id查询角色
     *
     * @param id id
     * @return {@link Role}
     */
    @RequestMapping("/{id}")
    public Role getRoleById(@PathVariable Integer id) {
        return rs.getById(id);
    }

    /**
     * 根据id删除角色
     *
     * @param id id
     * @return {@link Boolean}
     */
    @RequestMapping("/delete/{id}")
    public Boolean deleteRoleById(@PathVariable Integer id) {
        return rs.removeById(id);
    }

    /**
     * 添加角色
     *
     * @param role {@link Role}
     * @return {@link Boolean}
     */
    @RequestMapping("/add")
    public Boolean addRole(Role role) {
        return rs.save(role);
    }

    /**
     * 更新角色
     *
     * @param role {@link Role}
     * @return {@link Boolean}
     */
    @RequestMapping("/update")
    public Boolean updateRole(Role role) {
        return rs.updateById(role);
    }
}
