
/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.pay;


import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.*;
import com.egzosn.pay.common.bean.AssistOrder;
import com.egzosn.pay.common.bean.NoticeParams;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.RefundOrder;
import com.egzosn.pay.web.support.HttpRequestNoticeParams;
import net.totime.mail.request.QueryOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;


/**
 * 发起支付入口
 *
 * @author egan
 * email egzosn@gmail.com
 * date 2016/11/18 0:25
 */
@RestController
@RequestMapping("/pay/ali/v2/")
public class AliPayController {
    @Resource
    private AliPayService service;

    /**
     * 二维码支付
     *
     * @param price 金额
     * @return 二维码图像
     * @throws IOException 流异常
     */
    @RequestMapping(value = "qr_pay.jpg", produces = "image/jpeg;charset=UTF-8")
    public byte[] toQrPay(BigDecimal price) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(service.genQrPay(new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price, System.currentTimeMillis() + "", AliTransactionType.SWEEPPAY)), "JPEG", bos);
        return bos.toByteArray();
    }

    /**
     * 获取二维码链接
     *
     * @param price 金额
     * @return 二维码图像
     */
    @RequestMapping(value = "getQrPay.json")
    public String getQrPay(BigDecimal price) {
        //获取对应的支付账户操作工具（可根据账户id）
        return service.getQrPay(new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price, System.currentTimeMillis() + "", AliTransactionType.SWEEPPAY));
    }

    /**
     * 支付回调地址 方式一
     * <p>
     * 方式二，{@link #payBack(HttpServletRequest)} 是属于简化方式， 试用与简单的业务场景
     *
     * @param request 请求
     * @return 返回对应的响应码
     * @see #payBack(HttpServletRequest)
     */
    @Deprecated
    @RequestMapping(value = "payBackBefore.json")
    public String payBackBefore(HttpServletRequest request) {
        //获取支付方返回的对应参数
        NoticeParams noticeParams = service.getNoticeParams(new HttpRequestNoticeParams(request));
        if (null == noticeParams) {
            return service.getPayOutMessage("fail", "失败").toMessage();
        }
        //校验
        if (service.verify(noticeParams)) {
            //这里处理业务逻辑
            //......业务逻辑处理块........
            return service.successPayOutMessage(null).toMessage();
        }
        return service.getPayOutMessage("fail", "失败").toMessage();
    }

    /**
     * 支付回调地址
     *
     * @param request 请求
     * @return 是否成功
     * <p>
     * 业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     * <p>
     * 如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     * @throws IOException IOException
     */
    @Deprecated
    @RequestMapping(value = "payBackOld.json")
    public String payBackOld(HttpServletRequest request) throws IOException {
        //业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看com.egzosn.pay.common.api.PayService.setPayMessageHandler()
        return service.payBack(request.getParameterMap(), request.getInputStream()).toMessage();
    }

    /**
     * 支付回调地址
     *
     * @param request 请求
     * @return 是否成功
     * <p>
     * 业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     * <p>
     * 如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     */
    @RequestMapping(value = "payBack.json")
    public String payBack(HttpServletRequest request) {
        //业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看com.egzosn.pay.common.api.PayService.setPayMessageHandler()
        return service.payBack(new HttpRequestNoticeParams(request)).toMessage();
    }

    /**
     * 查询订单
     *
     * @param order 订单的请求体
     * @return 返回查询回来的结果集，支付方原值返回
     */
    @RequestMapping("query")
    public Map<String, Object> query(QueryOrder order) {
        return service.query(new AssistOrder(order.getTradeNo(), order.getOutTradeNo()));
    }

    /**
     * 统一收单交易结算接口
     *
     * @param order 订单的请求体
     * @return 返回查询回来的结果集，支付方原值返回
     */
    @RequestMapping("settle")
    public Map<String, Object> settle(OrderSettle order) {
        new OrderSettle();
        order.setTradeNo("支付宝单号");
        order.setOutRequestNo("商户单号");
        order.setAmount(new BigDecimal(100));
        order.setDesc("线下转账");
        return service.settle(order);
    }

    /**
     * 交易关闭接口
     *
     * @param order 订单的请求体
     * @return 返回支付方交易关闭后的结果
     */
    @RequestMapping("close")
    public Map<String, Object> close(QueryOrder order) {
        return service.close(new AssistOrder(order.getTradeNo(), order.getOutTradeNo()));
    }

    /**
     * 交易撤销接口
     *
     * @param order 订单的请求体
     * @return 返回支付方交易关闭后的结果
     */
    @RequestMapping("cancel")
    public Map<String, Object> cancel(QueryOrder order) {
        return service.cancel(order.getTradeNo(), order.getOutTradeNo());
    }

    /**
     * 申请退款接口
     *
     * @param order 订单的请求体
     * @return 返回支付方申请退款后的结果
     */
    @RequestMapping("refund")
    public AliRefundResult refund(RefundOrder order) {
        return service.refund(order);
    }

    /**
     * 查询退款
     *
     * @return 返回支付方查询退款后的结果
     */
    @RequestMapping("refundQuery")
    public Map<String, Object> refundQuery() {
        RefundOrder order = new RefundOrder();
        order.setOutTradeNo("我方系统商户单号");
        order.setTradeNo("支付宝单号");
        //退款金额
        order.setRefundAmount(new BigDecimal(1));
        order.setRefundNo("退款单号");
        order.setDescription("");
        return service.refundquery(order);
    }

    /**
     * 下载对账单
     *
     * @param order 订单的请求体
     * @return 返回支付方下载对账单的结果
     */
    @RequestMapping("downloadBill")
    public Object downloadBill(QueryOrder order) {
        return service.downloadBill(order.getBillDate(), order.getBillType());
    }

    /**
     * 转账
     *
     * @param order 转账订单
     * @return 对应的转账结果
     */
    @RequestMapping("transfer")
    public Map<String, Object> transfer(AliTransferOrder order) {
        order.setOutBizNo("转账单号");
        order.setTransAmount(new BigDecimal(10));
        order.setOrderTitle("转账业务的标题");
        order.setIdentity("参与方的唯一标识");
        order.setIdentityType("参与方的标识类型，目前支持如下类型：");
        order.setName("参与方真实姓名");
        order.setRemark("转账备注, 非必填");
        //单笔无密转账到支付宝账户
        order.setTransferType(AliTransferType.TRANS_ACCOUNT_NO_PWD);
        //单笔无密转账到银行卡
        order.setTransferType(AliTransferType.TRANS_BANKCARD_NO_PWD);
        return service.transfer(order);
    }

    /**
     * 转账查询
     *
     * @param outNo   商户转账订单号
     * @param tradeNo 支付平台转账订单号
     * @return 对应的转账订单
     */
    @RequestMapping("transferQuery")
    public Map<String, Object> transferQuery(String outNo, String tradeNo) {
        return service.transferQuery(outNo, tradeNo);
    }
}
