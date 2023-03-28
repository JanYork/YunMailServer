package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.UserToRole;
import net.totime.mail.mapper.UserToRoleMapper;
import net.totime.mail.service.UserToRoleService;
import org.springframework.stereotype.Service;

/**
 * (UserToRole)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 22:06:49
 */
@Service
public class UserToRoleServiceImpl extends ServiceImpl<UserToRoleMapper, UserToRole> implements UserToRoleService {

}