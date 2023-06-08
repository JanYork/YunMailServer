/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.config;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.common.util.sign.SignUtils;
import com.egzosn.pay.wx.v3.api.WxPayConfigStorage;
import com.egzosn.pay.wx.v3.api.WxPayService;
import net.totime.mail.handler.AliPayMessageHandler;
import net.totime.mail.handler.WxV3PayMessageHandler;
import net.totime.mail.interceptor.AliPayMessageInterceptor;
import net.totime.mail.properties.AliPayProperties;
import net.totime.mail.properties.WeiXinPayProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.file.Files.readString;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/30
 * @description 微信支付配置
 * @since 1.0.0
 */
@Configuration
public class PayConfigure {
    @Resource
    private WeiXinPayProperties wxp;
    @Resource
    private AliPayProperties alp;

    /**
     * 获取微信支付服务
     *
     * @return {@link WxPayService} 微信支付服务
     */
    @Bean
    public WxPayService getWxPayService() {
        WxPayConfigStorage payConfigStorage = new WxPayConfigStorage();
        payConfigStorage.setAppId(wxp.getAppId());
        payConfigStorage.setMchId(wxp.getMchId());
        payConfigStorage.setV3ApiKey(wxp.getApiKey());
        payConfigStorage.setNotifyUrl(wxp.getNotifyUrl());
        payConfigStorage.setReturnUrl(wxp.getReturnUrl());
        payConfigStorage.setInputCharset(wxp.getInputCharset());
        payConfigStorage.setCertSign(true);
        payConfigStorage.setApiClientKeyP12(wxp.getApiClientKeyP12());
        payConfigStorage.setCertStoreType(CertStoreType.PATH);
        WxPayService wxPayService = new WxPayService(payConfigStorage);
        wxPayService.setPayMessageHandler(new WxV3PayMessageHandler());
        return wxPayService;
    }

    /**
     * 获取支付宝支付配置
     *
     * @return {@link AliPayService} 支付宝支付配置
     */
    @Bean
    public AliPayService getAliPayService() {
        AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
        aliPayConfigStorage.setKeyPublic(this.getTxtString(alp.getKeyPublicPath()));
        aliPayConfigStorage.setKeyPrivate(this.getTxtString(alp.getKeyPrivatePath()));
        aliPayConfigStorage.setPid(alp.getPid());
        aliPayConfigStorage.setAppId(alp.getAppId());
        aliPayConfigStorage.setNotifyUrl(alp.getNotifyUrl());
        aliPayConfigStorage.setReturnUrl(alp.getReturnUrl());
        aliPayConfigStorage.setSignType(SignUtils.RSA2.name());
        aliPayConfigStorage.setSeller(alp.getSeller());
        aliPayConfigStorage.setInputCharset(alp.getInputCharset());
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        httpConfigStorage.setMaxTotal(20);
        httpConfigStorage.setDefaultMaxPerRoute(10);
        AliPayService aliPayService = new AliPayService(aliPayConfigStorage, httpConfigStorage);
        aliPayService.addPayMessageInterceptor(new AliPayMessageInterceptor());
        aliPayService.setPayMessageHandler(new AliPayMessageHandler());
        return aliPayService;
    }

    /**
     * 根据路径获取TXT文件内容
     *
     * @param path 路径
     * @return {@link String} TXT文件内容
     */
    private String getTxtString(String path) {
        try{
            return readString(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
