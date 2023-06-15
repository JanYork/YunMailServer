/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Oauth;
import net.totime.mail.mapper.OauthMapper;
import net.totime.mail.service.OauthService;
import org.springframework.stereotype.Service;

/**
 * 用户第三方授权表(Oauth)表服务实现类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:43
 */
@Service
public class OauthServiceImpl extends ServiceImpl<OauthMapper, Oauth> implements OauthService {

}
