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
import net.totime.mail.builder.WishOrdersBuilder;
import net.totime.mail.dto.WishDTO;
import net.totime.mail.entity.WishOrders;
import net.totime.mail.entity.User;
import net.totime.mail.entity.Wish;
import net.totime.mail.enums.*;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.handler.AliPayMessageHandler;
import net.totime.mail.handler.WxV3PayMessageHandler;
import net.totime.mail.pay.AliPayDefinedService;
import net.totime.mail.pay.WxPayDefinedService;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.UserService;
import net.totime.mail.service.WishOrdersService;
import net.totime.mail.service.WishService;
import net.totime.mail.util.CheckReturn;
import net.totime.mail.util.OrderNumberUtil;
import net.totime.mail.util.QrUtil;
import net.totime.mail.util.RedisUtil;
import net.totime.mail.vo.WishVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 许愿表(Wish)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-06-14 22:59:39
 */
@RestController
@RequestMapping("/user/wish")
@Api(tags = "[用户]许愿相关接口")
@Slf4j
public class WishApi {
    @Resource
    private WishService wishService;
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
    private WishOrdersService wishOrdersService;

    /**
     * 许愿投递
     *
     * @param wishDTO 许愿信息
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delivery")
    @ApiOperation("许愿投递")
    public ApiResponse<String> delivery(@RequestBody @Valid WishDTO wishDTO) {
        CheckReturn<Wish> check = check(wishDTO);
        if (!check.getStatus()) {
            return ApiResponse.<String>fail(null).message(check.getMsg());
        }
        Wish wish = check.getValue();
        wish.setUserId(StpUtil.getLoginIdAsLong());
        wish.setCreatTime(new Date());
        wish.setState(GlobalState.WAITING_FOR_PAYMENT.getState());
        boolean save = wishService.save(wish);
        return save ? ApiResponse.ok(wish.getId().toString()).message("许愿成功") : ApiResponse.<String>fail(null).message("系统异常");
    }

    /**
     * 许愿删除(软删除)
     *
     * @param wishId 许愿id
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @ApiOperation("许愿软删除")
    public ApiResponse<Boolean> delete(@RequestParam @Valid @NotNull(message = "许愿ID不能为空") String wishId) {
        Wish wish = wishService.getById(wishId);
        if (null == wish) {
            return ApiResponse.fail(false).message("许愿不存在");
        }
        if (wish.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            wish.setState(GlobalState.DELETED.getState());
            boolean update = wishService.updateById(wish);
            return update ? ApiResponse.ok(true).message("删除成功") : ApiResponse.fail(false).message("删除失败");
        }
        return ApiResponse.fail(false).message("心愿不存在");
    }

    /**
     * 许愿查询(根据ID)
     *
     * @param wishId 许愿id
     * @return {@link ApiResponse}<{@link WishVO}>
     */
    @GetMapping("/query/{wishId}")
    @ApiOperation("许愿查询(根据ID)")
    public ApiResponse<WishVO> query(@PathVariable @Valid @NotNull(message = "许愿ID不能为空") String wishId) {
        Wish wish = wishService.getById(Long.parseLong(wishId));
        if (null == wish) {
            return ApiResponse.<WishVO>fail(null).message("许愿不存在");
        }
        if (!wish.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            return ApiResponse.<WishVO>fail(null).message("许愿不存在");
        }
        if (wish.getState().equals(GlobalState.DELETED.getState())) {
            return ApiResponse.<WishVO>fail(null).message("许愿不存在");
        }
        return ApiResponse.ok(mapperFacade.map(wish, WishVO.class)).message("查询成功");
    }

    /**
     * 许愿查询(根据用户)
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ApiResponse}<{@link List}<{@link WishVO}>>
     */
    @GetMapping("/query/{page}/{size}")
    @ApiOperation("许愿查询(根据用户)")
    public ApiResponse<List<WishVO>> query(
            @PathVariable @Valid @NotNull(message = "页码不能为空") Integer page,
            @PathVariable @Valid @NotNull(message = "页数不能为空") Integer size) {
        List<Wish> wish = wishService.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<Wish>()
                        .eq(Wish::getUserId, StpUtil.getLoginIdAsLong())
                        .ne(Wish::getState, GlobalState.DELETED.getState())
                        .orderByDesc(Wish::getCreatTime)
        ).getRecords();
        return ApiResponse.ok(mapperFacade.mapAsList(wish, WishVO.class)).message("查询成功");
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
        WishOrders wishOrders = wishOrdersService.getOne(
                new LambdaQueryWrapper<WishOrders>()
                        .eq(WishOrders::getOrdersSerial, orderId)
                        .eq(WishOrders::getState, PayState.UNPAID.getValue())
                        .eq(WishOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (null == wishOrders) {
            return ApiResponse.fail(false).message("订单不存在");
        }
        wishOrders.setState(PayState.CANCEL.getValue());
        boolean update = wishOrdersService.updateById(wishOrders);
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
        WishOrders wishOrders = wishOrdersService.getOne(
                new LambdaQueryWrapper<WishOrders>()
                        .eq(WishOrders::getId, id)
                        .eq(WishOrders::getState, PayState.UNPAID.getValue())
                        .eq(WishOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (null == wishOrders) {
            return ApiResponse.fail(false).message("订单不存在");
        }
        wishOrders.setState(PayState.CANCEL.getValue());
        boolean update = wishOrdersService.updateById(wishOrders);
        return update ? ApiResponse.ok(true).message("取消成功") : ApiResponse.fail(false).message("取消失败");
    }

    /**
     * 获取支付URL
     *
     * @param wishId  许愿ID
     * @param payType 支付类型
     * @return {@link ApiResponse}<{@link String}>
     */
    @GetMapping("/pay")
    @ApiOperation("许愿支付")
    public ApiResponse<String> pay(
            @Valid @NotNull(message = "许愿ID不能为空") String wishId,
            @Valid @NotNull(message = "支付类型不能为空") Integer payType) {
        String payUrl = toPay(wishId, payType);
        if (StringUtils.isBlank(payUrl)) {
            return ApiResponse.fail("支付异常");
        }
        return ApiResponse.ok(payUrl);
    }

    /**
     * 获取支付二维码
     *
     * @param wishId  许愿ID
     * @param payType 支付类型
     * @return {@link byte[]}
     */
    @ApiOperation("许愿支付二维码")
    @GetMapping(value = "/pay/qrcode", produces = "image/jpeg;charset=UTF-8")
    public byte[] payQrCode(
            @Valid @NotNull(message = "许愿ID不能为空") String wishId,
            @Valid @NotNull(message = "支付类型不能为空") Integer payType) {
        String payUrl = toPay(wishId, payType);
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
     * @param wishDTO 许愿信息
     */
    private CheckReturn<Wish> check(WishDTO wishDTO) {
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        if (user.getPhone() == null) {
            return CheckReturn.fail("请先绑定手机号");
        }
        String code = (String) rut.get(KeyType.NORMAL.getKey() + user.getPhone());
        String key = KeyType.NORMAL.getKey() + user.getPhone();
        if (StringUtils.isBlank(code)) {
            return CheckReturn.fail("验证码已过期");
        }
        if (!code.equals(wishDTO.getCode())) {
            return CheckReturn.fail("验证码错误");
        }
        // 删除验证码
        rut.delete(key);
        return CheckReturn.ok(mapperFacade.map(wishDTO, Wish.class));
    }

    /**
     * 许愿支付
     *
     * @param wishId  许愿ID
     * @param payType 支付类型
     * @return {@link String}
     */
    private String toPay(String wishId, Integer payType) {
        if (StringUtils.isBlank(wishId)) {
            throw new GloballyUniversalException(500, "许愿ID不能为空");
        }
        PayType payTypeEnum = PayType.getPayTypeEnum(payType);
        if (payTypeEnum == null) {
            throw new GloballyUniversalException(500, "支付类型错误");
        }
        Long id = Long.valueOf(wishId);
        Wish wish = wishService.getById(id);
        if (wish == null) {
            throw new GloballyUniversalException(500, "许愿不存在");
        }
        if (!wish.getState().equals(GlobalState.WAITING_FOR_PAYMENT.getState())) {
            throw new GloballyUniversalException(500, "许愿状态异常");
        }
        WishOrdersBuilder build = WishOrdersBuilder.builder()
                .wishId(wish.getId())
                .ordersSerial(OrderNumberUtil.createOrderNumber(ScenarioType.SMS))
                .date(new Date())
                .payType(payTypeEnum.getId())
                .state(PayState.UNPAID.getValue())
                .userId(StpUtil.getLoginIdAsLong())
                .build();
        boolean save = wishOrdersService.save(mapperFacade.map(build, WishOrders.class));
        if (!save) {
            throw new GloballyUniversalException(500, "订单创建失败");
        }
        if (payType.equals(PayType.WX_PAY.getId())) {
            return wxPayService
                    .urlPath(PayCallbackUrlEnum.WX_WISH_CALLBACK_URL)
                    .backHandler(new WxV3PayMessageHandler.WishHandler())
                    .getQrPay(new PayOrder("云寄许愿", "云寄许愿与服务", PayAmount.WISH_PRICE.getAmount(), build.getOrdersSerial(), WxTransactionType.NATIVE));
        } else if (payType.equals(PayType.ALI_PAY.getId())) {
            return aliPayService
                    .urlPath(PayCallbackUrlEnum.ALI_WISH_CALLBACK_URL)
                    .backHandler(new AliPayMessageHandler.WishHandler())
                    .getQrPay(new PayOrder("云寄许愿", "云寄许愿与服务", PayAmount.WISH_PRICE.getAmount(), build.getOrdersSerial(), AliTransactionType.SWEEPPAY));
        } else {
            throw new GloballyUniversalException(500, "支付类型错误");
        }
    }

    /**
     * 许愿支付(订单)
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
        WishOrders orders = wishOrdersService.getOne(
                new LambdaQueryWrapper<WishOrders>()
                        .eq(WishOrders::getOrdersSerial, orderNumber)
                        .eq(WishOrders::getState, PayState.UNPAID.getValue())
                        .eq(WishOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (orders == null) {
            throw new GloballyUniversalException(500, "订单异常");
        }
        if (payType.equals(PayType.WX_PAY.getId())) {
            WxPayDefinedService pay = wxPayService
                    .urlPath(PayCallbackUrlEnum.WX_WISH_CALLBACK_URL)
                    .backHandler(new WxV3PayMessageHandler.WishHandler());
            String payUrl;
            if (pay.isEnablePolling()) {
                payUrl = pay.getQrPay(new PayOrder("云寄许愿", "云寄许愿服务", PayAmount.WISH_PRICE.getAmount(), orders.getOrdersSerial(), WxTransactionType.NATIVE), PayPollKey.DEFAULT);
            } else {
                payUrl = pay.getQrPay(new PayOrder("云寄许愿", "云寄许愿服务", PayAmount.WISH_PRICE.getAmount(), orders.getOrdersSerial(), WxTransactionType.NATIVE));
            }
            return payUrl;
        }
        if (payType.equals(PayType.ALI_PAY.getId())) {
            AliPayDefinedService pay = aliPayService
                    .urlPath(PayCallbackUrlEnum.ALI_WISH_CALLBACK_URL)
                    .backHandler(new AliPayMessageHandler.WishHandler());
            String payUrl;
            if (pay.isEnablePolling()) {
                payUrl = pay.getQrPay(new PayOrder("云寄许愿", "云寄许愿服务", PayAmount.WISH_PRICE.getAmount(), orders.getOrdersSerial(), AliTransactionType.SWEEPPAY), PayPollKey.DEFAULT);
            } else {
                payUrl = pay.getQrPay(new PayOrder("云寄许愿", "云寄许愿服务", PayAmount.WISH_PRICE.getAmount(), orders.getOrdersSerial(), AliTransactionType.SWEEPPAY));
            }
            return payUrl;
        }
        throw new GloballyUniversalException(500, "未知异常");
    }
}
