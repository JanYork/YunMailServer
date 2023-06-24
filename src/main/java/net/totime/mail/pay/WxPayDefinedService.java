/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.pay;

import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.wx.v3.api.WxPayConfigStorage;
import com.egzosn.pay.wx.v3.api.WxPayService;
import com.egzosn.pay.wx.v3.bean.response.WxPayMessage;
import net.totime.mail.context.SpringBeanContext;
import net.totime.mail.enums.PayCallbackUrlEnum;
import net.totime.mail.enums.PayPollKey;
import net.totime.mail.enums.PayState;
import net.totime.mail.enums.PayType;
import net.totime.mail.util.RedisUtil;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/17
 * @description 微信支付服务自定义层次
 * @see WxPayService
 * @since 1.0.0
 */
public class WxPayDefinedService extends WxPayService {
    /**
     * 是否开启订单轮询
     */
    private boolean enablePolling;

    /**
     * 创建支付服务
     *
     * @param payConfigStorage 微信对应的支付配置
     */
    public WxPayDefinedService(WxPayConfigStorage payConfigStorage) {
        super(payConfigStorage);
    }

    /**
     * 创建支付服务
     *
     * @param payConfigStorage 微信对应的支付配置
     * @param configStorage    微信对应的网络配置，包含代理配置、ssl证书配置
     */
    public WxPayDefinedService(WxPayConfigStorage payConfigStorage, HttpConfigStorage configStorage) {
        super(payConfigStorage, configStorage);
    }

    public String toPay(PayOrder order, PayPollKey pollKey) {
        if (enablePolling) {
            RedisUtil rut = SpringBeanContext.getBean(RedisUtil.class);
            rut.set(pollKey.getKey() + order.getOutTradeNo() + PayType.WX_PAY.getId(), PayState.UNPAID.getValue(), pollKey.getExpire());
        }
        return super.toPay(order);
    }

    public String getQrPay(PayOrder order, PayPollKey pollKey) {
        if (enablePolling) {
            RedisUtil rut = SpringBeanContext.getBean(RedisUtil.class);
            rut.set(pollKey.getKey() + order.getOutTradeNo() + PayType.WX_PAY.getId(), PayState.UNPAID.getValue(), pollKey.getExpire());
        }
        return super.getQrPay(order);
    }

    public boolean isEnablePolling() {
        return enablePolling;
    }

    public void setEnablePolling(boolean enablePolling) {
        this.enablePolling = enablePolling;
    }

    /**
     * 根据枚举改变支付回调URL配置
     *
     * @param urlEnum url枚举
     */
    public WxPayDefinedService urlPath(PayCallbackUrlEnum urlEnum) {
        WxPayConfigStorage payConfigStorage = getPayConfigStorage();
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
    public <T extends PayMessageHandler<WxPayMessage, WxPayService>> WxPayDefinedService backHandler(T handler) {
        setPayMessageHandler(handler);
        return this;
    }
}
