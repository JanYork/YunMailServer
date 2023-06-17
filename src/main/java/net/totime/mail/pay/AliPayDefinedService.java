/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.pay;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.wx.v3.api.WxPayService;
import net.totime.mail.enums.PayCallbackUrlEnum;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/17
 * @description 微信支付服务自定义层次
 * @see WxPayService
 * @since 1.0.0
 */
public class AliPayDefinedService extends AliPayService {
    /**
     * 创建支付服务
     *
     * @param payConfigStorage 微信对应的支付配置
     * @param configStorage    微信对应的网络配置，包含代理配置、ssl证书配置
     */
    public AliPayDefinedService(AliPayConfigStorage payConfigStorage, HttpConfigStorage configStorage) {
        super(payConfigStorage, configStorage);
    }

    /**
     * 创建支付服务
     *
     * @param payConfigStorage 微信对应的支付配置
     */
    public AliPayDefinedService(AliPayConfigStorage payConfigStorage) {
        super(payConfigStorage);
    }

    /**
     * 根据枚举改变支付回调URL配置
     *
     * @param urlEnum url枚举
     */
    public AliPayDefinedService urlPath(PayCallbackUrlEnum urlEnum) {
        AliPayConfigStorage payConfigStorage = getPayConfigStorage();
        payConfigStorage.setNotifyUrl(urlEnum.getValue());
        payConfigStorage.setReturnUrl(urlEnum.getValue());
        setPayConfigStorage(payConfigStorage);
        return this;
    }

    /**
     * 支付消息处理程序
     *
     * @param handler 处理程序
     * @return {@link AliPayDefinedService}
     */
    public <T extends PayMessageHandler<AliPayMessage, AliPayService>> AliPayDefinedService backHandler(T handler) {
        setPayMessageHandler(handler);
        return this;
    }
}
