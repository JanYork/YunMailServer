/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.login;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xkcoding.justauth.AuthRequestFactory;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.utils.AuthStateUtils;
import net.totime.mail.domain.auth.OauthOperateService;
import net.totime.mail.dto.AuthCallBackDTO;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.vo.AuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/30
 * @description 第三方登录接口
 * @since 1.0.0
 */
@Controller
@Slf4j
@RequestMapping("/oauth")
@Api(tags = "云寄第三方注册与登录接口")
@CrossOrigin
@ApiSupport(author = "JanYork")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OauthApi {
    private final AuthRequestFactory factory;
    @Resource
    private WxMaService wxService;
    @Resource
    private OauthOperateService oos;

    /**
     * 第三方登录列表
     *
     * @return {@link ApiResponse}<{@link List}<{@link String}>> 第三方登录列表
     */
    @GetMapping
    @ResponseBody
    public ApiResponse<List<String>> list() {
        return ApiResponse.ok(factory.oauthList());
    }

    /**
     * 第三方登录
     *
     * @param type 类型
     */
    @GetMapping("/login/{type}")
    @ResponseBody
    public ApiResponse<String> login(@PathVariable String type) {
        return ApiResponse.ok(factory.get(type).authorize(AuthStateUtils.createState()));
    }

    /**
     * 第三方登录回调
     *
     * @param type     类型
     * @param callback 回调
     * @return {@link AuthResponse} 登录响应
     */
    @RequestMapping("/{type}/callback")
    @RequestBody
    public String login(@PathVariable String type, AuthCallback callback, Model model) {
        AuthResponse login = factory.get(type).login(callback);
        if (login.ok()) {
            AuthCallBackDTO auth = convert(login);
            AuthVO authVO = oos.login(auth);
            model.addAttribute("auth", authVO);
        }
        return "login";
    }

    @GetMapping("/wx/mini")
    @ResponseBody
    public ApiResponse<HashMap<String, String>> wxMini(@RequestParam String code) {
        WxMaJscode2SessionResult sessionInfo;
        try {
            sessionInfo = wxService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, String> res = new HashMap<>(16);
        res.put("openid", sessionInfo.getOpenid());
        res.put("session_key", sessionInfo.getSessionKey());
        return ApiResponse.ok(res);
    }

    private AuthCallBackDTO convert(AuthResponse login) {
        AuthCallBackDTO auth = new AuthCallBackDTO();
        AuthCallBackDTO.TokenInfo authToken = new AuthCallBackDTO.TokenInfo();
        JSONObject data = JSON.parseObject(JSON.toJSONString(login.getData()));
        JSONObject token = data.getJSONObject("token");
        auth.setUuid(data.getString("uuid"));
        auth.setNickname(data.getString("nickname"));
        auth.setAvatar(data.getString("avatar"));
        auth.setEmail(data.getString("email"));
        auth.setRemark(data.getString("remark"));
        auth.setSource(data.getString("source"));
        authToken.setAccessToken(token.getString("accessToken"));
        authToken.setExpireIn(token.getInteger("expireIn"));
        authToken.setRefreshToken(token.getString("refreshToken"));
        authToken.setRefreshTokenExpireIn(token.getInteger("refreshTokenExpireIn"));
        authToken.setOpenId(token.getString("openId"));
        auth.setToken(authToken);
        return auth;
    }

    @GetMapping("/wx/mp")
    @ResponseBody
    public ApiResponse<HashMap<String, String>> wxMpLogin(){
        return null;
    }
}
