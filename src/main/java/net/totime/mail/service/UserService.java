package net.totime.mail.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
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
    @Cached(name = "user::", key = "#entity.id", expire = 3600)
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
    @CacheInvalidate(name = "user::", key = "#entity.id")
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
    @CacheUpdate(name = "user::", key = "#entity.id", value = "#entity")
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
    @Cached(name = "user::", key = "#id", expire = 3600)
    default User getById(Serializable id) {
        return IService.super.getById(id);
    }
}
