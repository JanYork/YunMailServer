/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.annotation.RateLimiter;
import net.totime.mail.dto.IdCardAuthDTO;
import net.totime.mail.entity.IdCardAuth;
import net.totime.mail.entity.User;
import net.totime.mail.enums.IdCardAuthStatus;
import net.totime.mail.enums.UserState;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.exception.RuntimeExceptionToMsgException;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.IdCardAuthService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.CheckReturn;
import net.totime.mail.util.OkHttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/13
 * @description 实名认证接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user/auth")
@Api(tags = "[用户]实名认证接口")
@Slf4j
public class IdCardAuthApi {
    @Value("${third.three-element.url}")
    private String threeElementUrl;
    @Value("${third.two-element.url}")
    private String twoElementUrl;
    @Value("${third.app-code}")
    private String appcode;
    @Resource
    private IdCardAuthService authService;
    @Resource
    private UserService userService;

    /**
     * 实名认证
     *
     * @param authInfo 实名认证要素
     * @return {@link ApiResponse} 实名认证结果
     */
    @PostMapping("/idCard")
    @RateLimiter(count = 3, time = 3600)
    @ApiOperation(value = "实名认证", notes = "未成年可二要素或填写其他证明身份图片，开启人工审核，成年人必须三要素匹配")
    public ApiResponse<Boolean> idCardAuth(@RequestBody @Valid IdCardAuthDTO authInfo) {
        CheckReturn<Boolean> checkReturn = preCheck(authInfo);
        if (!checkReturn.getStatus()) {
            return ApiResponse.fail(false).message(checkReturn.getMsg());
        }
        boolean adult = isAdult(authInfo.getIdCard());
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        if (user == null) {
            return ApiResponse.fail(false).message("用户不存在");
        }
        if (authInfo.getOther() != null) {
            // 人工审核模式
            postOperation(authInfo, user);
            return ApiResponse.ok(true).message("认证成功，等待人工审核").code(1);
        }
        if (adult) {
            CheckReturn<Boolean> auth = auth(authInfo.getIdCard(), authInfo.getName(), user.getPhone());
            if (!auth.getStatus()) {
                return ApiResponse.fail(false).message(auth.getMsg());
            }
            Boolean r = auth.getValue();
            if (!r) {
                return ApiResponse.fail(false).message("认证失败");
            }
            // 后置操作
            boolean save = postOperation(authInfo, user);
            if (!save) {
                return ApiResponse.fail(false).message("认证异常");
            }
            return ApiResponse.ok(true).message("认证成功");
        } else {
            CheckReturn<Boolean> auth = auth(authInfo.getIdCard(), authInfo.getName());
            if (!auth.getStatus()) {
                return ApiResponse.fail(false).message(auth.getMsg());
            }
            Boolean r = auth.getValue();
            if (!r) {
                return ApiResponse.fail(false).message("认证失败");
            }
            // 后置操作
            boolean save = postOperation(authInfo, user);
            if (!save) {
                return ApiResponse.fail(false).message("认证异常");
            }
            return ApiResponse.ok(true).message("认证成功");
        }
    }

    /**
     * 实名认证-三要素
     *
     * @param idCard 身份证
     * @param name   名字
     * @param phone  电话
     * @return {@link CheckReturn}<{@link Boolean}>
     */
    public CheckReturn<Boolean> auth(String idCard, String name, String phone) {
        Map<String, String> param = new HashMap<>(4);
        param.put("idcard", idCard);
        param.put("mobile", phone);
        param.put("name", name);

        String response = OkHttpUtil.aliPost(appcode, threeElementUrl, param);
        if (StringUtils.isEmpty(response)) {
            return CheckReturn.fail("认证异常");
        }

        HashMap<String, Object> result = JSONObject.parseObject(response, new TypeReference<>() {
        });

        if (!"0".equals(result.get("code"))) {
            return CheckReturn.fail("认证异常");
        }
        HashMap<String, String> res = JSONObject.parseObject(JSONObject.toJSONString(result.get("result")), new TypeReference<>() {
        });
        String authCode = res.get("res");
        boolean r = "1".equals(authCode);
        if (r) {
            return CheckReturn.ok(true);
        } else {
            return CheckReturn.ok(false);
        }
    }

    /**
     * 实名认证-二要素
     *
     * @param idCard 身份证
     * @param name   姓名
     * @return {@link ApiResponse}<{@link String}> 实名认证结果
     */
    public CheckReturn<Boolean> auth(String idCard, String name) {
        Map<String, String> param = new HashMap<>(4);
        param.put("cardNo", idCard);
        param.put("realName", name);
        String response = OkHttpUtil.aliPost(appcode, twoElementUrl, param);
        if (StringUtils.isEmpty(response)) {
            return CheckReturn.fail("认证异常");
        }
        HashMap<String, Object> result = JSONObject.parseObject(response, new TypeReference<>() {
        });
        if (0 != (int) result.get("error_code")) {
            return CheckReturn.fail("认证异常");
        }
        HashMap<String, Object> res = JSONObject.parseObject(JSONObject.toJSONString(result.get("result")), new TypeReference<>() {
        });
        return (Boolean) res.get("isok") ? CheckReturn.ok(true) : CheckReturn.ok(false);
    }

    /**
     * 是否成年
     *
     * @param idCard 身份证
     * @return boolean
     */
    private boolean isAdult(String idCard) {
        String birthday = idCard.substring(6, 14);
        int year = Integer.parseInt(birthday.substring(0, 4));
        int month = Integer.parseInt(birthday.substring(4, 6));
        int day = Integer.parseInt(birthday.substring(6));
        birthday = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
        // 转换为时间格式
        Date date;
        try{
            date = DateUtil.parse(birthday, DatePattern.NORM_DATE_PATTERN);
        }catch (Exception e){
            throw new GloballyUniversalException(500,"证件号异常");
        }
        // 获取年龄
        int age = DateUtil.ageOfNow(date);
        return age >= 18;
    }

    /**
     * 前置检查
     *
     * @param cardInfo 卡信息
     * @return {@link CheckReturn}<{@link Boolean}>
     */
    private CheckReturn<Boolean> preCheck(IdCardAuthDTO cardInfo) {
        // 获取当前用户
        User user = userService.getOne(
                new LambdaQueryWrapper<>(new User())
                        .eq(User::getId, StpUtil.getLoginIdAsLong())
                        .eq(User::getState, UserState.NORMAL.getCode())
        );
        if (user == null) {
            return CheckReturn.fail("用户异常");
        }
        boolean adult = isAdult(cardInfo.getIdCard());
        if (adult) {
            if (user.getPhone() == null) {
                return CheckReturn.fail("请先绑定手机号");
            }
        }
        // 判断是否已经实名认证
        if (user.getAuthRealName()) {
            return CheckReturn.fail("已实名认证");
        }
        // 判断是否已经提交过实名认证
        if (user.getIdCardAuthId() != 0) {
            return CheckReturn.fail("已提交实名认证");
        }
        return CheckReturn.ok(true);
    }

    /**
     * 后置操作
     */
    private boolean postOperation(IdCardAuthDTO cardInfo, User user) {
        IdCardAuth authInfo = new IdCardAuth();
        authInfo.setUserId(user.getId());
        authInfo.setIdCard(cardInfo.getIdCard());
        authInfo.setName(cardInfo.getName());
        if (user.getPhone() != null) {
            authInfo.setPhone(user.getPhone());
        }
        // 如果存在其他实名资料，转人工
        if (cardInfo.getOther() != null) {
            authInfo.setOther(cardInfo.getOther());
            authInfo.setStatus(IdCardAuthStatus.PENDING.getCode());
            boolean save = authService.save(authInfo);
            if (!save) {
                log.error("存储实名认证失败，身份证：{}，姓名：{}，手机号：{}，认证时间：{}", cardInfo.getIdCard(), cardInfo.getName(), user.getPhone(), DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
                throw new RuntimeExceptionToMsgException("实名认证写入数据失败", "实名");
            }
            user.setIdCardAuthId(authInfo.getId());
            user.setAuthRealName(false);
            boolean update = userService.updateById(user);
            if (!update) {
                log.error("存储实名认证失败，身份证：{}，姓名：{}，手机号：{}，认证时间：{}", cardInfo.getIdCard(), cardInfo.getName(), user.getPhone(), DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
                throw new RuntimeExceptionToMsgException("实名认证更新用户数据失败", "实名");
            }
            return true;
        } else {
            boolean save = authService.save(authInfo);
            if (!save) {
                log.error("实名认证失败，身份证：{}，姓名：{}，手机号：{}，认证时间：{}", cardInfo.getIdCard(), cardInfo.getName(), user.getPhone(), DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
                throw new RuntimeExceptionToMsgException("实名认证写入数据失败", "实名");
            }
            user.setIdCardAuthId(authInfo.getId());
            user.setAuthRealName(true);
            boolean update = userService.updateById(user);
            if (!update) {
                log.error("实名认证失败，身份证：{}，姓名：{}，手机号：{}，认证时间：{}", cardInfo.getIdCard(), cardInfo.getName(), user.getPhone(), DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
                throw new RuntimeExceptionToMsgException("实名认证更新用户数据失败", "实名");
            }
            return true;
        }
    }
}
