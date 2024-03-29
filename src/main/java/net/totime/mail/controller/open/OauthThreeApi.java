/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.open;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.annotation.SaIgnore;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xkcoding.justauth.AuthRequestFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.utils.AuthStateUtils;
import net.totime.mail.annotation.SaAdminCheckLogin;
import net.totime.mail.annotation.SaAdminCheckRole;
import net.totime.mail.domain.OauthOperate;
import net.totime.mail.dto.AuthCallBackDTO;
import net.totime.mail.enums.*;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.handler.WxMessageHandler;
import net.totime.mail.pojo.WxMpLoginInfo;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.util.CodeUtil;
import net.totime.mail.util.RedisUtil;
import net.totime.mail.vo.AuthVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
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
@Api(tags = "[开放]云寄第三方OAUTH登录")
@CrossOrigin
@ApiSupport(author = "JanYork")
@SaIgnore
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OauthThreeApi {
    private final AuthRequestFactory factory;
    @Resource
    private WxMaService wxService;
    @Resource
    private WxMpService wxMpService;
    @Resource
    private OauthOperate oos;
    @Resource
    private RedisUtil rut;
    @Resource
    private WxMessageHandler wxMessageHandler;

    /**
     * 第三方登录列表
     *
     * @return {@link ApiResponse}<{@link List}<{@link String}>> 第三方登录列表
     */
    @GetMapping
    @ResponseBody
    @ApiOperation(value = "获取第三方登录类型列表")
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
    @ApiOperation(value = "第三方登录吊起")
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
    @ApiOperation(value = "第三方登录回调", notes = "第三方登录回调，返回子页面，利用父子窗口通信实现登录回调前端")
    public String login(@PathVariable String type, AuthCallback callback, Model model) {
        @SuppressWarnings("rawtypes") AuthResponse login = factory.get(type).login(callback);
        if (login.ok()) {
            AuthCallBackDTO auth = convert(login);
            AuthVO authVO = oos.login(auth);
            model.addAttribute("auth", authVO);
        }
        return "login";
    }

    @GetMapping("/wx/mini")
    @ResponseBody
    @ApiOperation(value = "微信小程序登录（未完成）")
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

    /**
     * 登录参数转换
     *
     * @param login 登录参数
     * @return {@link AuthCallBackDTO}
     */
    @SuppressWarnings("rawtypes")
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

    /**
     * 微信公众号登录(校验码模式)永久二维码
     *
     * @return {@link ApiResponse}<{@link String}>
     */
    @SneakyThrows
    @GetMapping("/wx/mp/code")
    @ResponseBody
    @ApiOperation(value = "微信公众号登录(校验码模式)永久二维码")
    @SaAdminCheckRole("super_admin")
    @SaAdminCheckLogin
    public ApiResponse<String> wxMpLogin() {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(WxMpSceneEnum.LOGIN.getScene());
        String ticket = wxMpQrCodeTicket.getTicket();
        String url = wxMpService.getQrcodeService().qrCodePictureUrl(ticket);
        return ApiResponse.ok(url);
    }

    /**
     * 微信公众号登录(扫码模式)
     *
     * @return {@link ApiResponse}<{@link String}>
     */
    @SneakyThrows
    @GetMapping("/wx/mp")
    @ResponseBody
    @ApiOperation(value = "微信公众号登录(扫码模式)")
    public ApiResponse<HashMap<String, String>> wxMpLoginScan() {
        String code = CodeUtil.generateCode(10);
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket("wl" + code, 300);
        String ticket = wxMpQrCodeTicket.getTicket();
        String url = wxMpService.getQrcodeService().qrCodePictureUrl(ticket);
        rut.set("wl" + code, code, 180L);
        rut.set(code, new WxMpLoginInfo(LoginState.NOT_LOGIN.getState(), null), 300L);
        HashMap<String, String> res = new HashMap<>(6);
        res.put("state", code);
        res.put("url", url);
        return ApiResponse.ok(res);
    }

    /**
     * 微信公众号登录(扫码模式)获取状态轮询接口
     *
     * @return {@link ApiResponse}<{@link String}>
     */
    @GetMapping("/wx/mp/state")
    @ResponseBody
    @ApiOperation(value = "微信公众号登录(扫码模式)获取状态轮询接口")
    public ApiResponse<String> wxMpLoginState(String code) {
        WxMpLoginInfo info = (WxMpLoginInfo) rut.get(code);
        if (info == null) {
            return ApiResponse.ok("登录异常").code(-2).message("系统错误");
        }
        if (info.getState() == LoginState.LOGGED_IN.getState()) {
            rut.delete(code);
            String token = wxUnionIdLogin(info.getToken());
            return ApiResponse.ok(token).message("登录成功");
        } else if (info.getState() == LoginState.NOT_LOGIN.getState()) {
            return ApiResponse.ok("未登录").message("Go On").code(-1);
        }
        return ApiResponse.ok("登录异常").code(-2).message("未知错误");
    }

    /**
     * 微信公众号登录(授权码模式)获取状态接口
     *
     * @param code 授权码
     * @return {@link ApiResponse}<{@link String}>
     */
    @GetMapping("/wx/mp/state/{code}")
    @ResponseBody
    @ApiOperation(value = "微信公众号登录(授权码模式)获取状态接口")
    public ApiResponse<String> wxMpLoginStateCode(@PathVariable String code) {
        if (code == null) {
            return ApiResponse.fail("空参错误").message("授权码不能为空");
        }
        String key = "wx_" + code;
        String unionId = (String) rut.get(key);
        if (unionId == null) {
            return ApiResponse.fail("授权码错误").message("授权码已过期");
        }
        String token = wxUnionIdLogin(unionId);
        if (token == null) {
            return ApiResponse.fail("登录失败").message("系统未知错误");
        }
        rut.delete(key);
        return ApiResponse.ok(token).message("登录成功");
    }

    /**
     * 微信公众号登录回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @SneakyThrows
    @PostMapping("/wx/mp/callback")
    @ResponseBody
    @ApiOperation(value = "微信公众号登录回调")
    public String wxMpLoginCallback(HttpServletRequest request) {
        WxMpXmlMessage message = WxMpXmlMessage.fromXml(request.getInputStream());
        // 登录事件
        if (isToLogin(message)) {
            String openId = message.getFromUser();
            Long incr = rut.incr("wx_login" + openId, 60L);
            if (incr > 5) {
                WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                        .TEXT()
                        .toUser(message.getFromUser())
                        .fromUser(message.getToUser())
                        .content("哼！你已经超出了时空机的启动次数，请休息一下再试试吧！")
                        .build();
                return texts.toXml();
            }
            String code = CodeUtil.generateCodeMixHalf(6);
            // 缓存授权码对应的唯一标识
            rut.set("wx_" + code, message.getFromUser(), 120L);
            LocalDateTime now = LocalDateTime.now();
            String time = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss SSS"));
            WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                    .TEXT()
                    .toUser(message.getFromUser())
                    .fromUser(message.getToUser())
                    .content("获取成功，欢迎开启云寄时空裂缝，本次裂缝预计在两分钟左右被空间法则修复，请尽快使用哦!" +
                            "\n你不在的日子里，发生了很多很多事情呢，快来看看吧!\n" +
                            "时空节点" + time +
                            "\n授权码：" + code)
                    .build();
            return texts.toXml();
        }
        // 登录事件(扫码模式)
        if (isToLoginTmp(message)) {
            // 获取场景值
            String code = message.getEventKey();
            // 获取授权码
            String authCode = (String) rut.get(code);
            if (StringUtils.isEmpty(authCode)) {
                WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                        .TEXT()
                        .toUser(message.getFromUser())
                        .fromUser(message.getToUser())
                        .content("哎呀呀，这个时空裂缝貌似已经被修复了呢，本次登录已经失效了哦！\n" +
                                "尝试重新获取登录二维码或者寻找其他可用的时空裂缝吧！")
                        .build();
                return texts.toXml();
            }
            // 删除场景值缓存
            rut.delete(code);
            String token = message.getFromUser();
            if (StringUtils.isEmpty(token)) {
                WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                        .TEXT()
                        .toUser(message.getFromUser())
                        .fromUser(message.getToUser())
                        .content("哎呀呀，云寄时光机出现了故障呢，本次登录失败了！\n" +
                                "尝试重新获取登录二维码或者向云寄官方反馈吧！")
                        .build();
                return texts.toXml();
            }
            // 设置登录信息
            rut.set(authCode, new WxMpLoginInfo(LoginState.LOGGED_IN.getState(), token), 300L);
            LocalDateTime now = LocalDateTime.now();
            String time = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss SSS"));
            WxMpXmlOutTextMessage texts = WxMpXmlOutTextMessage
                    .TEXT()
                    .toUser(message.getFromUser())
                    .fromUser(message.getToUser())
                    .content("登陆成功，欢迎回到云寄星球!" +
                            "\n你不在的日子里，发生了很多很多事情呢，快来看看吧!" +
                            "\n有任何问题，欢迎联系时空管理员哦！\n" +
                            "节点碎片" + time)
                    .build();
            return texts.toXml();
        }
        return wxMessageHandler.handler(message);
    }

    /**
     * 是否是登录事件
     *
     * @param message 消息对象
     * @return boolean
     */
    private boolean isToLogin(WxMpXmlMessage message) {
        return (
                message.getMsgType().equals(WxMessageType.EVENT.getType()) &&
                        message.getEvent().equals(WxMessageEvent.SCAN.getEvent()) &&
                        message.getEventKey().equals(WxMpSceneEnum.LOGIN.getScene())
        );
    }

    /**
     * 是否是临时二维码场景值登录事件
     *
     * @param message 消息对象
     * @return boolean
     */
    private boolean isToLoginTmp(WxMpXmlMessage message) {
        return (
                message.getMsgType().equals(WxMessageType.EVENT.getType()) &&
                        message.getEvent().equals(WxMessageEvent.SCAN.getEvent()) &&
                        message.getEventKey().startsWith("wl")
        );
    }

    /**
     * 微信openId登录处理
     *
     * @param openId 唯一标识
     * @return token {@link String} token
     */
    private String wxUnionIdLogin(String openId) {
        AuthVO login = oos.login(openId, OauthType.WECHAT_MP);
        if (ObjectUtils.isEmpty(login)) {
            log.error("微信登录失败，失败原因：{}", "微信登录接口返回空");
            throw new GloballyUniversalException(500, "未知异常");
        }
        if (login.getCode() != 200) {
            log.error("微信登录失败，失败原因：{}", login.getMsg());
            throw new GloballyUniversalException(500, login.getMsg());
        }
        if (login.getToken() == null) {
            log.error("微信登录失败，失败原因：{}", login.getMsg());
            throw new GloballyUniversalException(500, login.getMsg());
        }
        return login.getToken().getTokenValue();
    }


    /**
     * 微信验证签名
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return boolean
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String token = "YunMailToTime2023ByJanYork";
        List<String> tmpArr = Arrays.asList(token, timestamp, nonce);
        Collections.sort(tmpArr);
        String tmpStr = String.join("", tmpArr);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = digest.digest(tmpStr.getBytes());
            String calculatedSignature = bytesToHex(hashBytes);

            return calculatedSignature.equals(signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 字节转十六进制
     *
     * @param bytes 字节
     * @return {@link String}
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
