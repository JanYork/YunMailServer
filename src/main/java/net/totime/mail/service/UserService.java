/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.totime.mail.entity.User;

import java.io.Serializable;

/**
 * (User)表服务接口
 *
 * @author JanYork
 * @since 2023-03-26 17:53:58
 */
public interface UserService extends IService<User> {
    /**
     * 保存实体对象
     *
     * @param entity 实体对象
     * @return boolean 是否成功
     */
    @Override
    default boolean save(User entity) {
        return IService.super.save(entity);
    }

    /**
     * 根据 ID 删除
     *
     * @param entity 实体对象
     * @return boolean 是否成功
     */
    @Override
    default boolean removeById(User entity) {
        return IService.super.removeById(entity);
    }

    /**
     * 根据 ID 修改
     *
     * @param entity 实体
     * @return boolean
     */
    @Override
    default boolean updateById(User entity) {
        return IService.super.updateById(entity);
    }

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return {@link User}
     */
    @Override
    default User getById(Serializable id) {
        return IService.super.getById(id);
    }
}
