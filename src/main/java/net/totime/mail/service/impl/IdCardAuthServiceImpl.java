/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.IdCardAuth;
import net.totime.mail.mapper.IdCardAuthMapper;
import net.totime.mail.service.IdCardAuthService;
import org.springframework.stereotype.Service;

/**
 * 实名认证表(IdCardAuth)表服务实现类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:35
 */
@Service
public class IdCardAuthServiceImpl extends ServiceImpl<IdCardAuthMapper, IdCardAuth> implements IdCardAuthService {

}
