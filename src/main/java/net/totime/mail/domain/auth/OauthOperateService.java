package net.totime.mail.domain.auth;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.totime.mail.dto.AuthCallBackDTO;
import net.totime.mail.entity.Oauth;
import net.totime.mail.entity.User;
import net.totime.mail.enums.OauthType;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.OauthService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.BcryptUtil;
import net.totime.mail.util.SnowflakeUtil;
import net.totime.mail.vo.AuthVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/02
 * @description 第三方登录操作服务
 * @since 1.0.0
 */
@Service
public class OauthOperateService {
    @Resource
    private OauthService oauthService;
    @Resource
    private UserService userService;

    /**
     * 第三方登录
     *
     * @param auth 身份信息
     * @return {@link ApiResponse}  登录结果
     */
    public AuthVO login(AuthCallBackDTO auth) {
        String isLoginForId = (String) StpUtil.getLoginIdDefaultNull();
        if (isLoginForId != null) {
            return bind(auth);
        }
        User user = userBuilder(auth);
        long snowflakeId = SnowflakeUtil.getSnowflakeId();
        user.setId(snowflakeId);
        if (!userService.save(user)) {
            return AuthVO.builder().code(500).msg("登录失败").build();
        }
        Oauth oauth = oauthBuilder(auth);
        oauth.setUserId(snowflakeId);
        if (!oauthService.save(oauth)) {
            return AuthVO.builder().code(500).msg("登录失败").build();
        }
        StpUtil.login(snowflakeId);
        return AuthVO.builder().code(200).msg("登录成功").token(StpUtil.getTokenValue()).build();
    }

    /**
     * 第三方登录绑定
     *
     * @param auth 身份信息
     * @return {@link ApiResponse}  登录结果
     */
    public AuthVO bind(AuthCallBackDTO auth) {
        Long userId = Long.parseLong(StpUtil.getLoginId().toString());
        //查询是否已经绑定
        if (isBind(userId)) {
            return AuthVO.builder().code(500).msg("已经绑定").build();
        }
        Oauth oauth = oauthBuilder(auth);
        oauth.setUserId(userId);
        if (!oauthService.save(oauth)) {
            return AuthVO.builder().code(500).msg("绑定失败").build();
        }
        return AuthVO.builder().code(200).msg("绑定成功").build();
    }

    /**
     * 查询第三方登录是否已经绑定
     *
     * @param id 用户id
     * @return {@link Boolean} 是否绑定
     */
    private Boolean isBind(Long id) {
        return oauthService.getOne(new QueryWrapper<Oauth>().eq("user_id", id)) != null;
    }

    /**
     * 获取用户id
     *
     * @param id 第三方登录唯一标识
     * @return {@link Long} 用户id
     */
    private Long getUserId(String id) {
        Oauth oauth = oauthService.getOne(new QueryWrapper<Oauth>().eq("open_id", id));
        if (oauth == null) {
            return null;
        }
        return oauth.getUserId();
    }

    /**
     * 构建第三方登录实体
     *
     * @param auth 身份信息
     * @return {@link Oauth} 第三方登录实体
     */
    private Oauth oauthBuilder(AuthCallBackDTO auth) {
        Oauth oauth = new Oauth();
        oauth.setOpenId(auth.getToken().getOpenId());
        oauth.setProvider(OauthType.valueOf(auth.getSource().toUpperCase()));
        oauth.setAccessToken(auth.getToken().getAccessToken());
        oauth.setRefreshToken(auth.getToken().getRefreshToken());
        oauth.setTokenExpiry(auth.getToken().getExpireIn());
        oauth.setOpenId(auth.getUuid());
        oauth.setCreatedAt(new Date());
        oauth.setUpdatedAt(new Date());
        return oauth;
    }

    private User userBuilder(AuthCallBackDTO auth) {
        User user = new User();
        user.setAvatar(auth.getAvatar());
        user.setName(auth.getNickname());
        user.setEmail(auth.getEmail());
        user.setCreateTime(new Date());
        //填充随机密码与手机号，防止用户未绑定手机号
        String pwd = UUID.randomUUID().toString().substring(0, 8);
        String gensalt = BcryptUtil.gensalt();
        user.setPwd(BcryptUtil.encrypt(pwd, gensalt));
        user.setSalt(gensalt);
        user.setPhone("0");
        return user;
    }
}