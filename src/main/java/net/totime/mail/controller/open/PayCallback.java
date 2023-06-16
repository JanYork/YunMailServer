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
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.handler.WxV3PayMessageHandler;
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
@RequestMapping("/pay")
public class PayCallback {
    @Resource
    private WxPayService wxPayservice;
    @Resource
    private AliPayService aliPayService;
    @Resource
    private MapperFacade mapperFacade;

    /**
     * 信件微信支付回调
     *
     * @param request {@link HttpServletRequest} 请求
     * @return {@link String} 支付回调结果
     */
    @RequestMapping("/wx/callback")
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
    public String wxPayBackBySponsor(HttpServletRequest request) {
        WxPayService service = mapperFacade.map(wxPayservice, WxPayService.class);
        service.setPayMessageHandler(new WxV3PayMessageHandler.SponsorHandler());
        return service.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 赞助支付宝回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @RequestMapping(value = "/ali/sponsor/callback")
    public String aliPayBackBySponsor(HttpServletRequest request) {
        AliPayService service = mapperFacade.map(aliPayService, AliPayService.class);
        service.setPayMessageHandler(new WxV3PayMessageHandler.SponsorHandler());
        return service.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 心愿微信支付回调
     *
     * @param request {@link HttpServletRequest} 请求
     * @return {@link String} 支付回调结果
     */
    @RequestMapping("/wx/wish/callback")
    public String wxPayBackByWish(HttpServletRequest request) {
        WxPayService service = mapperFacade.map(wxPayservice, WxPayService.class);
        service.setPayMessageHandler(new WxV3PayMessageHandler.WishHandler());
        return service.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 心愿支付宝回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @RequestMapping(value = "/ali/wish/callback")
    public String aliPayBackByWish(HttpServletRequest request) {
        AliPayService service = mapperFacade.map(aliPayService, AliPayService.class);
        service.setPayMessageHandler(new WxV3PayMessageHandler.WishHandler());
        return service.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 短信微信支付回调
     *
     * @param request {@link HttpServletRequest} 请求
     * @return {@link String} 支付回调结果
     */
    @RequestMapping("/wx/msg/callback")
    public String wxPayBackByMsg(HttpServletRequest request) {
        WxPayService service = mapperFacade.map(wxPayservice, WxPayService.class);
        service.setPayMessageHandler(new WxV3PayMessageHandler.MessageHandler());
        return service.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 短信支付宝回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @RequestMapping(value = "/ali/msg/callback")
    public String aliPayBackByMsg(HttpServletRequest request) {
        AliPayService service = mapperFacade.map(aliPayService, AliPayService.class);
        service.setPayMessageHandler(new WxV3PayMessageHandler.MessageHandler());
        return service.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }
}
