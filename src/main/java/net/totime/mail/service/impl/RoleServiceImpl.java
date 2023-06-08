/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Role;
import net.totime.mail.mapper.RoleMapper;
import net.totime.mail.service.RoleService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * (Role)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:01
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public boolean save(Role entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Role entity) {
        return super.removeById(entity);
    }

    @Override
    public boolean updateById(Role entity) {
        return super.updateById(entity);
    }

    @Override
    public Role getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public Role getOne(Wrapper<Role> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public List<Role> list() {
        return super.list();
    }
}
