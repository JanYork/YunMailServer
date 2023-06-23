/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.v3.bean.WxTransactionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.builder.MessageOrdersBuilder;
import net.totime.mail.dto.MessageDTO;
import net.totime.mail.entity.Message;
import net.totime.mail.entity.MessageOrders;
import net.totime.mail.entity.User;
import net.totime.mail.enums.*;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.handler.AliPayMessageHandler;
import net.totime.mail.handler.WxV3PayMessageHandler;
import net.totime.mail.pay.AliPayDefinedService;
import net.totime.mail.pay.WxPayDefinedService;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.MessageOrdersService;
import net.totime.mail.service.MessageService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.CheckReturn;
import net.totime.mail.util.OrderNumberUtil;
import net.totime.mail.util.QrUtil;
import net.totime.mail.util.RedisUtil;
import net.totime.mail.vo.MessageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/23
 * @description 信息相关接口
 * @since 1.0.0 TODO：支付
 */
@RestController
@RequestMapping("/user/message")
@Api(tags = "[用户]短信相关接口")
@Slf4j
public class MessageApi {
    @Resource
    private MessageService messageService;
    @Resource
    private UserService userService;
    @Resource
    private MapperFacade mapperFacade;
    @Resource
    private RedisUtil rut;
    @Resource
    private AliPayDefinedService aliPayService;
    @Resource
    private WxPayDefinedService wxPayService;
    @Resource
    private MessageOrdersService messageOrdersService;

    /**
     * 短信投递
     *
     * @param messageDTO 短信信息
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delivery")
    @ApiOperation("短信投递")
    public ApiResponse<String> delivery(@RequestBody @Valid MessageDTO messageDTO) {
        CheckReturn<Message> check = check(messageDTO);
        if (!check.getStatus()) {
            return ApiResponse.<String>fail(null).message(check.getMsg());
        }
        Message message = check.getValue();
        message.setUserId(StpUtil.getLoginIdAsLong());
        message.setCreateTime(new Date());
        message.setState(GlobalState.WAITING_FOR_PAYMENT.getState());
        boolean save = messageService.save(message);
        return save ? ApiResponse.ok(message.getId().toString()).message("投递成功") : ApiResponse.<String>fail(null).message("系统异常");
    }

    /**
     * 短信删除(软删除)
     *
     * @param messageId 短信id
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @ApiOperation("短信软删除")
    public ApiResponse<Boolean> delete(@RequestParam @Valid @NotNull(message = "短信ID不能为空") String messageId) {
        Message message = messageService.getById(messageId);
        if (null == message) {
            return ApiResponse.fail(false).message("短信不存在");
        }
        if (message.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            message.setState(GlobalState.DELETED.getState());
            boolean update = messageService.updateById(message);
            return update ? ApiResponse.ok(true).message("删除成功") : ApiResponse.fail(false).message("删除失败");
        }
        return ApiResponse.fail(false).message("短信不存在");
    }

    /**
     * 短信查询(根据ID)
     *
     * @param messageId 短信id
     * @return {@link ApiResponse}<{@link MessageVO}>
     */
    @GetMapping("/query/{messageId}")
    @ApiOperation("短信查询(根据ID)")
    public ApiResponse<MessageVO> query(@PathVariable @Valid @NotNull(message = "短信ID不能为空") String messageId) {
        Message message = messageService.getById(Long.parseLong(messageId));
        if (null == message) {
            return ApiResponse.<MessageVO>fail(null).message("短信不存在");
        }
        if (!message.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            return ApiResponse.<MessageVO>fail(null).message("短信不存在");
        }
        if (message.getState().equals(GlobalState.DELETED.getState())) {
            return ApiResponse.<MessageVO>fail(null).message("短信不存在");
        }
        return ApiResponse.ok(mapperFacade.map(message, MessageVO.class)).message("查询成功");
    }

    /**
     * 短信查询(根据用户)
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ApiResponse}<{@link List}<{@link MessageVO}>>
     */
    @GetMapping("/query/{page}/{size}")
    @ApiOperation("短信查询(根据用户)")
    public ApiResponse<List<MessageVO>> query(
            @PathVariable @Valid @NotNull(message = "页码不能为空") Integer page,
            @PathVariable @Valid @NotNull(message = "页数不能为空") Integer size) {
        List<Message> message = messageService.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<Message>()
                        .eq(Message::getUserId, StpUtil.getLoginIdAsLong())
                        .ne(Message::getState, GlobalState.DELETED.getState())
                        .orderByDesc(Message::getCreateTime)
        ).getRecords();
        return ApiResponse.ok(mapperFacade.mapAsList(message, MessageVO.class)).message("查询成功");
    }

    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/cancel")
    @ApiOperation("取消订单")
    public ApiResponse<Boolean> cancel(@RequestParam @Valid @NotNull(message = "订单ID不能为空") String orderId) {
        MessageOrders messageOrders = messageOrdersService.getOne(
                new LambdaQueryWrapper<MessageOrders>()
                        .eq(MessageOrders::getOrdersSerial, orderId)
                        .eq(MessageOrders::getState, PayState.UNPAID.getValue())
                        .eq(MessageOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (null == messageOrders) {
            return ApiResponse.fail(false).message("订单不存在");
        }
        messageOrders.setState(PayState.CANCEL.getValue());
        boolean update = messageOrdersService.updateById(messageOrders);
        return update ? ApiResponse.ok(true).message("取消成功") : ApiResponse.fail(false).message("取消失败");
    }

    /**
     * 取消订单
     *
     * @param id 订单主键
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/cancelById")
    @ApiOperation("取消订单")
    public ApiResponse<Boolean> cancelById(@RequestParam @Valid @NotNull(message = "订单ID不能为空") String id) {
        MessageOrders messageOrders = messageOrdersService.getOne(
                new LambdaQueryWrapper<MessageOrders>()
                        .eq(MessageOrders::getId, id)
                        .eq(MessageOrders::getState, PayState.UNPAID.getValue())
                        .eq(MessageOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (null == messageOrders) {
            return ApiResponse.fail(false).message("订单不存在");
        }
        messageOrders.setState(PayState.CANCEL.getValue());
        boolean update = messageOrdersService.updateById(messageOrders);
        return update ? ApiResponse.ok(true).message("取消成功") : ApiResponse.fail(false).message("取消失败");
    }

    /**
     * 获取支付URL
     *
     * @param messageId 短信ID
     * @param payType   支付类型
     * @return {@link ApiResponse}<{@link String}>
     */
    @GetMapping("/pay")
    @ApiOperation("短信支付")
    public ApiResponse<String> pay(
            @Valid @NotNull(message = "短信ID不能为空") String messageId,
            @Valid @NotNull(message = "支付类型不能为空") Integer payType) {
        String payUrl = toPay(messageId, payType);
        if (StringUtils.isBlank(payUrl)) {
            return ApiResponse.fail("支付异常");
        }
        return ApiResponse.ok(payUrl);
    }

    /**
     * 获取支付二维码
     *
     * @param messageId 短信ID
     * @param payType   支付类型
     * @return {@link byte[]}
     */
    @ApiOperation("短信支付二维码")
    @GetMapping(value = "/pay/qrcode", produces = "image/jpeg;charset=UTF-8")
    public byte[] payQrCode(
            @Valid @NotNull(message = "短信ID不能为空") String messageId,
            @Valid @NotNull(message = "支付类型不能为空") Integer payType) {
        String payUrl = toPay(messageId, payType);
        if (StringUtils.isBlank(payUrl)) {
            throw new GloballyUniversalException(500, "支付异常");
        }
        return QrUtil.getByteArrayWithLogo(payUrl);
    }

    /**
     * 获取支付URL
     *
     * @param orderNumber 订单号
     * @param payType     支付类型
     * @return {@link ApiResponse}<{@link String}>
     */
    @GetMapping("/pay/order")
    @ApiOperation("订单支付")
    public ApiResponse<String> payOrder(
            @Valid @NotNull(message = "订单号不能为空") String orderNumber,
            @Valid @NotNull(message = "支付类型不能为空") Integer payType) {
        String payUrl = toPayByOrder(orderNumber, payType);
        if (StringUtils.isBlank(payUrl)) {
            return ApiResponse.fail("支付异常");
        }
        return ApiResponse.ok(payUrl);
    }

    /**
     * 获取支付二维码
     *
     * @param orderNumber 订单号
     * @param payType     支付类型
     * @return {@link byte[]}
     */
    @ApiOperation("订单支付二维码")
    @GetMapping(value = "/pay/order/qrcode", produces = "image/jpeg;charset=UTF-8")
    public byte[] payOrderQrCode(
            @Valid @NotNull(message = "订单号不能为空") String orderNumber,
            @Valid @NotNull(message = "支付类型不能为空") Integer payType) {
        String payUrl = toPayByOrder(orderNumber, payType);
        if (StringUtils.isBlank(payUrl)) {
            throw new GloballyUniversalException(500, "支付异常");
        }
        return QrUtil.getByteArrayWithLogo(payUrl);
    }

    /**
     * 校验
     *
     * @param messageDTO 短信信息
     */
    private CheckReturn<Message> check(MessageDTO messageDTO) {
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        if (user.getPhone() == null) {
            return CheckReturn.fail("请先绑定手机号");
        }
        String code = (String) rut.get(KeyType.NORMAL.getKey() + user.getPhone());
        String key = KeyType.NORMAL.getKey() + user.getPhone();
        if (StringUtils.isBlank(code)) {
            return CheckReturn.fail("验证码已过期");
        }
        if (!code.equals(messageDTO.getCode())) {
            return CheckReturn.fail("验证码错误");
        }
        // 删除验证码
        rut.delete(key);
        return CheckReturn.ok(mapperFacade.map(messageDTO, Message.class));
    }

    /**
     * 短信支付
     *
     * @param messageId 短信ID
     * @param payType   支付类型
     * @return {@link String}
     */
    private String toPay(String messageId, Integer payType) {
        if (StringUtils.isBlank(messageId)) {
            throw new GloballyUniversalException(500, "短信ID不能为空");
        }
        PayType payTypeEnum = PayType.getPayTypeEnum(payType);
        if (payTypeEnum == null) {
            throw new GloballyUniversalException(500, "支付类型错误");
        }
        Long id = Long.valueOf(messageId);
        Message message = messageService.getById(id);
        if (message == null) {
            throw new GloballyUniversalException(500, "短信不存在");
        }
        if (!message.getState().equals(GlobalState.WAITING_FOR_PAYMENT.getState())) {
            throw new GloballyUniversalException(500, "短信状态异常");
        }
        MessageOrdersBuilder build = MessageOrdersBuilder.builder()
                .messageId(message.getId())
                .ordersSerial(OrderNumberUtil.createOrderNumber(ScenarioType.SMS))
                .date(new Date())
                .payType(payTypeEnum.getId())
                .state(PayState.UNPAID.getValue())
                .userId(StpUtil.getLoginIdAsLong())
                .build();
        boolean save = messageOrdersService.save(mapperFacade.map(build, MessageOrders.class));
        if (!save) {
            throw new GloballyUniversalException(500, "订单创建失败");
        }
        if (payType.equals(PayType.WX_PAY.getId())) {
            return wxPayService
                    .urlPath(PayCallbackUrlEnum.WX_SMS_CALLBACK_URL)
                    .backHandler(new WxV3PayMessageHandler.MessageHandler())
                    .getQrPay(new PayOrder("云寄短信", "云寄短信与服务", PayAmount.SMS_PRICE.getAmount(), build.getOrdersSerial(), WxTransactionType.NATIVE));
        } else if (payType.equals(PayType.ALI_PAY.getId())) {
            return aliPayService
                    .urlPath(PayCallbackUrlEnum.ALI_SMS_CALLBACK_URL)
                    .backHandler(new AliPayMessageHandler.MessageHandler())
                    .getQrPay(new PayOrder("云寄短信", "云寄短信与服务", PayAmount.SMS_PRICE.getAmount(), build.getOrdersSerial(), AliTransactionType.SWEEPPAY));
        } else {
            throw new GloballyUniversalException(500, "支付类型错误");
        }
    }

    /**
     * 短信支付(订单)
     *
     * @param orderNumber 订单号
     * @param payType     支付类型
     * @return {@link String}
     */
    private String toPayByOrder(String orderNumber, Integer payType) {
        PayType payTypeEnum = PayType.getPayTypeEnum(payType);
        if (payTypeEnum == null) {
            throw new GloballyUniversalException(500, "支付类型错误");
        }
        MessageOrders orders = messageOrdersService.getOne(
                new LambdaQueryWrapper<MessageOrders>()
                        .eq(MessageOrders::getOrdersSerial, orderNumber)
                        .eq(MessageOrders::getState, PayState.UNPAID.getValue())
                        .eq(MessageOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (orders == null) {
            throw new GloballyUniversalException(500, "订单异常");
        }
        if (payType.equals(PayType.WX_PAY.getId())) {
            return wxPayService
                    .urlPath(PayCallbackUrlEnum.WX_SMS_CALLBACK_URL)
                    .backHandler(new WxV3PayMessageHandler.MessageHandler())
                    .getQrPay(new PayOrder("云寄短信", "云寄短信服务", PayAmount.SMS_PRICE.getAmount(), orders.getOrdersSerial(), WxTransactionType.NATIVE));
        }
        if (payType.equals(PayType.ALI_PAY.getId())) {
            return aliPayService
                    .urlPath(PayCallbackUrlEnum.ALI_SMS_CALLBACK_URL)
                    .backHandler(new AliPayMessageHandler.MessageHandler())
                    .getQrPay(new PayOrder("云寄短信", "云寄短信服务", PayAmount.SMS_PRICE.getAmount(), orders.getOrdersSerial(), AliTransactionType.SWEEPPAY));
        }
        throw new GloballyUniversalException(500, "未知异常");
    }
}
