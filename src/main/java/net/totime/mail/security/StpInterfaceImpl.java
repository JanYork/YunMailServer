package net.totime.mail.security;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.totime.mail.entity.Perm;
import net.totime.mail.entity.Role;
import net.totime.mail.entity.UserToPrem;
import net.totime.mail.entity.UserToRole;
import net.totime.mail.service.PermService;
import net.totime.mail.service.RoleService;
import net.totime.mail.service.UserToPremService;
import net.totime.mail.service.UserToRoleService;
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

    /**
     * 一个账号所拥有的权限码集合
     *
     * @param loginId   登录id
     * @param loginType 登录类型
     * @return {@link List}<{@link String}> 权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list = userToPrem.listObjs(new QueryWrapper<UserToPrem>().eq("user_id", loginId).lambda().select(UserToPrem::getPremId), Object::toString);
        return perm.listObjs(new QueryWrapper<Perm>().in("id", list).lambda().select(Perm::getPermissions), Object::toString);
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
        List<String> list = userToRole.listObjs(new QueryWrapper<UserToRole>().eq("user_id", loginId).lambda().select(UserToRole::getRoleId), Object::toString);
        return role.listObjs(new QueryWrapper<Role>().in("id", list).lambda().select(Role::getName), Object::toString);
    }
}