/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
     * @return {@link ApiResponse}  登录结果
     */
    public AuthVO login(AuthCallBackDTO auth) {
        String isLoginForId = (String) StpUtil.getLoginIdDefaultNull();
        //已登录->走绑定->已经绑定 ? 回调已绑定 : 走绑定流程
        if (isLoginForId != null) {
            return bind(auth);
        }
        //未登录->已绑定->走登录
        Oauth oauth = ouathIsBind(auth);
        if (!ObjectUtils.isEmpty(oauth)) {
            Long userId = oauth.getUserId();
            StpUtil.login(userId);
            return AuthVO.builder().code(200).msg("登录成功").token(StpUtil.getTokenInfo()).build();
        }
        //未登录->未绑定->走注册
        User user = userBuilder(auth);
        long snowflakeId = SnowflakeUtil.getSnowflakeId();
        user.setId(snowflakeId);
        if (!userService.save(user)) {
            return AuthVO.builder().code(500).msg("登录失败").build();
        }
        //构建第三方登录信息实体
        Oauth o = oauthBuilder(auth);
        o.setUserId(snowflakeId);
        if (!oauthService.save(o)) {
            return AuthVO.builder().code(500).msg("登录失败").build();
        }
        StpUtil.login(snowflakeId);
        return AuthVO.builder().code(200).msg("登录成功").token(StpUtil.getTokenInfo()).build();
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
     * 获取第三方登录信息
     *
     * @param auth 身份信息
     * @return {@link Oauth} 第三方登录信息
     */
    private Oauth ouathIsBind(AuthCallBackDTO auth) {
        OauthType provider = OauthType.valueOf(auth.getSource().toUpperCase());
        return oauthService.getOne(
                new QueryWrapper<Oauth>()
                        .eq("provider", provider)
                        .eq("open_id", auth.getUuid())
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
        return ObjectUtils.isNotEmpty(
                oauthService.getOne(
                        new QueryWrapper<Oauth>()
                                .eq("user_id", userId)
                                .eq("provider", provider)
                )
        );
    }
}
