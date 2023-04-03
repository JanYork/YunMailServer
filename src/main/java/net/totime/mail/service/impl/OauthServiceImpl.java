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
 * @since 2023-04-02 10:38:46
 */
@Service
public class OauthServiceImpl extends ServiceImpl<OauthMapper, Oauth> implements OauthService {

}