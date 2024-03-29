/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.open;

import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.web.support.HttpRequestNoticeParams;
import com.egzosn.pay.wx.v3.api.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/18
 * @description 支付回调接口
 * @since 1.0.0
 */
@RestController
@Slf4j
@RequestMapping("/api/v2/pay")
@Api(tags = "[回调]支付回调接口")
public class PayCallback {
    @Resource
    private WxPayService wxPayservice;
    @Resource
    private AliPayService aliPayService;

    /**
     * 信件微信支付回调
     *
     * @param request {@link HttpServletRequest} 请求
     * @return {@link String} 支付回调结果
     */
    @RequestMapping("/wx/callback")
    @ApiOperation("信件微信支付回调")
    public String wxPayBack(HttpServletRequest request) {
        return wxPayservice.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 信件支付宝回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @RequestMapping(value = "/ali/callback")
    @ApiOperation("信件支付宝回调")
    public String aliPayBack(HttpServletRequest request) {
        return aliPayService.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 赞助微信支付回调
     *
     * @param request {@link HttpServletRequest} 请求
     * @return {@link String} 支付回调结果
     */
    @RequestMapping("/wx/sponsor/callback")
    @ApiOperation("赞助微信支付回调")
    public String wxPayBackBySponsor(HttpServletRequest request) {
        return wxPayservice.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 赞助支付宝回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @RequestMapping(value = "/ali/sponsor/callback")
    @ApiOperation("赞助支付宝回调")
    public String aliPayBackBySponsor(HttpServletRequest request) {
        return aliPayService.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 心愿微信支付回调
     *
     * @param request {@link HttpServletRequest} 请求
     * @return {@link String} 支付回调结果
     */
    @RequestMapping("/wx/wish/callback")
    @ApiOperation("心愿微信支付回调")
    public String wxPayBackByWish(HttpServletRequest request) {
        return wxPayservice.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 心愿支付宝回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @RequestMapping(value = "/ali/wish/callback")
    @ApiOperation("心愿支付宝回调")
    public String aliPayBackByWish(HttpServletRequest request) {
        return aliPayService.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 短信微信支付回调
     *
     * @param request {@link HttpServletRequest} 请求
     * @return {@link String} 支付回调结果
     */
    @RequestMapping("/wx/msg/callback")
    @ApiOperation("短信微信支付回调")
    public String wxPayBackByMsg(HttpServletRequest request) {
        return wxPayservice.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 短信支付宝回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @RequestMapping(value = "/ali/msg/callback")
    @ApiOperation("短信支付宝回调")
    public String aliPayBackByMsg(HttpServletRequest request) {
        return aliPayService.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }
}
