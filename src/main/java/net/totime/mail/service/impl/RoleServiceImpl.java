package net.totime.mail.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
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
    @Cached(name = "role:", key = "#entity.id", expire = 3600)
    public boolean save(Role entity) {
        return super.save(entity);
    }

    @Override
    @CacheInvalidate(name = "role:", key = "#entity.id")
    public boolean removeById(Role entity) {
        return super.removeById(entity);
    }

    @Override
    @CacheUpdate(name = "role:", key = "#entity.id", value = "#entity")
    public boolean updateById(Role entity) {
        return super.updateById(entity);
    }

    @Override
    @Cached(name = "role:", key = "#id", expire = 3600)
    public Role getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public Role getOne(Wrapper<Role> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    @Cached(name = "role:", key = "list", expire = 3600)
    public List<Role> list() {
        return super.list();
    }
}
