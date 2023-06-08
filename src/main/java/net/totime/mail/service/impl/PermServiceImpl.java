/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Perm;
import net.totime.mail.mapper.PermMapper;
import net.totime.mail.service.PermService;
import org.springframework.stereotype.Service;

/**
 * (Perm)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:02
 */
@Service
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements PermService {

}
