/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.SneakyThrows;
import net.totime.mail.dto.AuthCallBackDTO;
import net.totime.mail.entity.Oauth;
import net.totime.mail.entity.User;
import net.totime.mail.enums.OauthType;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.OauthService;
import net.totime.mail.service.UserService;
import net.totime.mail.storage.tencent.service.TencentUpload;
import net.totime.mail.util.BcryptUtil;
import net.totime.mail.util.GenerateAvatar;
import net.totime.mail.util.NameRandom;
import net.totime.mail.util.SnowflakeUtil;
import net.totime.mail.vo.AuthVO;
import org.apache.commons.lang3.ObjectUtils;
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
public class OauthOperate {
    @Resource
    private OauthService oauthService;
    @Resource
    private UserService userService;
    @Resource
    private TencentUpload tu;

    /**
     * 第三方登录
     *
     * @param auth 身份信息
     * @return {@link AuthVO}
     */
    public AuthVO login(AuthCallBackDTO auth) {
        boolean isLogin = StpUtil.isLogin();
        //已登录->走绑定->已经绑定 ? 回调已绑定 : 走绑定流程
        if (isLogin) {
            return bind(auth);
        }
        //未登录->已绑定->走登录
        Oauth oauth = ouathIsBind(auth);
        if (ObjectUtils.isNotEmpty(oauth)) {
            Long userId = oauth.getUserId();
            StpUtil.login(userId);
            return AuthVO.builder().code(200).msg("登录成功").token(StpUtil.getTokenInfo()).build();
        }
        long snowflakeId = SnowflakeUtil.getSnowflakeId();
        boolean register = toRegister(snowflakeId, auth);
        if (!register) {
            return AuthVO.builder().code(500).msg("注册失败").build();
        }
        StpUtil.login(snowflakeId);
        return AuthVO.builder().code(200).msg("登录成功").token(StpUtil.getTokenInfo()).build();
    }

    /**
     * 第三方登录(微信)
     *
     * @param openId 微信unionId
     * @return {@link AuthVO}
     */
    public AuthVO login(String openId, OauthType oauthType) {
        boolean isLogin = StpUtil.isLogin();
        //已登录->走绑定->已经绑定 ? 回调已绑定 : 走绑定流程
        if (isLogin) {
            return bind(openId, oauthType);
        }
        //未登录->已绑定->走登录
        Oauth oauth = ouathIsBind(openId, oauthType);
        if (ObjectUtils.isNotEmpty(oauth)) {
            Long userId = oauth.getUserId();
            StpUtil.login(userId);
            return AuthVO.builder().code(200).msg("登录成功").token(StpUtil.getTokenInfo()).build();
        }
        //未登录->未绑定->走注册
        long snowflakeId = SnowflakeUtil.getSnowflakeId();
        boolean register = toRegister(snowflakeId, openId, oauthType);
        if (!register) {
            return AuthVO.builder().code(500).msg("登录失败").build();
        }
        StpUtil.login(snowflakeId);
        // 此处事务未提交，所以 StpUtil.getTokenInfo() 无法获取到用户信息
        return AuthVO.builder().code(200).msg("登录成功").token(StpUtil.getTokenInfo()).build();
    }

    /**
     * 注册
     *
     * @param snowflakeId 雪花id
     * @param openId      第三方登录唯一标识
     * @param oauthType   oauth类型
     * @return boolean
     */
    public boolean toRegister(long snowflakeId, String openId, OauthType oauthType) {
        User user = userBuilder(openId);
        user.setId(snowflakeId);
        if (!userService.save(user)) {
            return false;
        }
        //构建第三方登录信息实体
        Oauth o = oauthBuilder(openId, oauthType);
        o.setUserId(snowflakeId);
        return oauthService.save(o);
    }

    /**
     * 注册
     *
     * @param snowflakeId 雪花id
     * @param auth        身份验证
     * @return boolean
     */
    public boolean toRegister(long snowflakeId, AuthCallBackDTO auth) {
        //未登录->未绑定->走注册
        User user = userBuilder(auth);
        user.setId(snowflakeId);
        if (!userService.save(user)) {
            return false;
        }
        //构建第三方登录信息实体
        Oauth o = oauthBuilder(auth);
        o.setUserId(snowflakeId);
        return oauthService.save(o);
    }

    /**
     * 第三方登录绑定
     *
     * @param auth 身份信息
     * @return {@link ApiResponse}  登录结果
     */
    public AuthVO bind(AuthCallBackDTO auth) {
        Long userId = Long.parseLong(StpUtil.getLoginId().toString());
        OauthType provider = OauthType.valueOf(auth.getSource().toUpperCase());
        //查询是否已经绑定
        if (ouathIsBindByUid(userId, provider)) {
            return AuthVO.builder().code(500).msg("已经绑定").build();
        }
        //构建第三方登录实体
        Oauth oauth = oauthBuilder(auth);
        oauth.setUserId(userId);
        if (!oauthService.save(oauth)) {
            return AuthVO.builder().code(500).msg("绑定失败").build();
        }
        return AuthVO.builder().code(200).msg("绑定成功").build();
    }

    /**
     * 第三方登录绑定
     *
     * @param openId    微信unionId
     * @param oauthType 第三方登录类型
     * @return {@link AuthVO}
     */
    public AuthVO bind(String openId, OauthType oauthType) {
        Long userId = Long.parseLong(StpUtil.getLoginId().toString());
        //查询是否已经绑定
        if (ouathIsBindByUid(userId, oauthType)) {
            return AuthVO.builder().code(500).msg("已经绑定").build();
        }
        //构建第三方登录实体
        Oauth oauth = oauthBuilder(openId, oauthType);
        oauth.setUserId(userId);
        if (!oauthService.save(oauth)) {
            return AuthVO.builder().code(500).msg("绑定失败").build();
        }
        return AuthVO.builder().code(200).msg("绑定成功").build();
    }

    /**
     * 构建第三方登录实体
     *
     * @param auth 身份信息
     * @return {@link Oauth} 第三方登录实体
     */
    private Oauth oauthBuilder(AuthCallBackDTO auth) {
        Oauth oauth = new Oauth();
        oauth.setProvider(OauthType.valueOf(auth.getSource().toUpperCase()));
        oauth.setAccessToken(auth.getToken().getAccessToken());
        oauth.setRefreshToken(auth.getToken().getRefreshToken());
        oauth.setTokenExpiry(auth.getToken().getExpireIn());
        oauth.setOpenId(auth.getUuid());
        oauth.setCreatedAt(new Date());
        oauth.setUpdatedAt(new Date());
        return oauth;
    }

    /**
     * 构建第三方登录实体
     *
     * @param openId    第三方登录唯一标识
     * @param oauthType 第三方登录类型
     * @return {@link Oauth}
     */
    private Oauth oauthBuilder(String openId, OauthType oauthType) {
        Oauth oauth = new Oauth();
        oauth.setProvider(oauthType);
        oauth.setOpenId(openId);
        oauth.setCreatedAt(new Date());
        oauth.setUpdatedAt(new Date());
        return oauth;
    }

    /**
     * 构建用户实体
     *
     * @param auth 身份信息
     * @return {@link User} 用户实体
     */
    @SneakyThrows
    private User userBuilder(AuthCallBackDTO auth) {
        User user = new User();
        user.setAvatar(tu.upload(auth.getAvatar()));
        user.setNickName(auth.getNickname());
        user.setName(auth.getSource() + "_" + auth.getUuid());
        user.setEmail(auth.getEmail());
        user.setCreateTime(new Date());
        //填充随机密码与手机号，防止用户未绑定手机号
        String pwd = UUID.randomUUID().toString().substring(0, 8);
        user.setPwd(BcryptUtil.encrypt(pwd));
        user.setPhone("0");
        return user;
    }

    /**
     * 构建用户实体
     *
     * @param openId 第三方登录唯一标识
     * @return {@link User}
     */
    @SneakyThrows
    private User userBuilder(String openId) {
        User user = new User();
        user.setAvatar(tu.upload(GenerateAvatar.generateAvatar((long) openId.hashCode())));
        //UUID去掉-
        UUID uuid = UUID.randomUUID();
        user.setNickName("微信用户" + "_" + uuid.toString().replace("-", ""));
        user.setName(NameRandom.randomName("Yun"));
        user.setCreateTime(new Date());
        //填充随机密码与手机号，防止用户未绑定手机号
        String pwd = UUID.randomUUID().toString().substring(0, 8);
        user.setPwd(BcryptUtil.encrypt(pwd));
        return user;
    }

    /**
     * 获取第三方登录信息
     *
     * @param auth 身份信息
     * @return {@link Oauth} 第三方登录信息
     */
    private Oauth ouathIsBind(AuthCallBackDTO auth) {
        OauthType provider = OauthType.valueOf(auth.getSource().toUpperCase());
        return oauthService.getOne(
                new LambdaQueryWrapper<>(Oauth.class)
                        .eq(Oauth::getProvider, provider)
                        .eq(Oauth::getOpenId, auth.getUuid())
        );
    }

    /**
     * 获取第三方登录信息
     *
     * @param openId    第三方登录唯一标识
     * @param oauthType 第三方登录类型
     * @return {@link Oauth}
     */
    private Oauth ouathIsBind(String openId, OauthType oauthType) {
        return oauthService.getOne(
                new LambdaQueryWrapper<>(Oauth.class)
                        .eq(Oauth::getProvider, oauthType)
                        .eq(Oauth::getOpenId, openId)
        );
    }

    /**
     * 判断用户是否已经绑定
     *
     * @param userId   用户id
     * @param provider 第三方登录类型
     * @return Boolean 是否已经绑定
     */
    private Boolean ouathIsBindByUid(Long userId, OauthType provider) {
        Oauth one = oauthService.getOne(
                new LambdaQueryWrapper<>(Oauth.class)
                        .eq(Oauth::getUserId, userId)
                        .eq(Oauth::getProvider, provider)
        );
        return one != null;
    }
}
