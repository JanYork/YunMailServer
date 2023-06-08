/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.UserToPrem;
import net.totime.mail.mapper.UserToPremMapper;
import net.totime.mail.service.UserToPremService;
import org.springframework.stereotype.Service;

/**
 * (UserToPrem)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 22:24:57
 */
@Service
public class UserToPremServiceImpl extends ServiceImpl<UserToPremMapper, UserToPrem> implements UserToPremService {

}
