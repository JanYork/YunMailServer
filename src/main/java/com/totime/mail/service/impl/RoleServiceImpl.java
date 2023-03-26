package com.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.totime.mail.entity.Role;
import com.totime.mail.mapper.RoleMapper;
import com.totime.mail.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * (Role)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:01
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}