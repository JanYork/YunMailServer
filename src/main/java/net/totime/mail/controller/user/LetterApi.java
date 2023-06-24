/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.v3.bean.WxTransactionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.builder.LetterOrdersBuilder;
import net.totime.mail.dto.LetterChangeDTO;
import net.totime.mail.dto.LetterDTO;
import net.totime.mail.entity.Letter;
import net.totime.mail.entity.LetterOrders;
import net.totime.mail.entity.User;
import net.totime.mail.enums.*;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.handler.BaiDuAiHandler;
import net.totime.mail.pay.AliPayDefinedService;
import net.totime.mail.pay.WxPayDefinedService;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.LetterOrdersService;
import net.totime.mail.service.LetterService;
import net.totime.mail.service.LetterTypeService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.CheckReturn;
import net.totime.mail.util.OrderNumberUtil;
import net.totime.mail.util.QrUtil;
import net.totime.mail.util.RedisUtil;
import net.totime.mail.vo.LetterVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/17
 * @description 信件相关接口
 * @since 1.0.0
 */
@Api(tags = "[用户]信件相关接口")
@RestController
@RequestMapping("/user/letter")
@Validated
public class LetterApi {
    @Resource
    private LetterService letterService;
    @Resource
    private LetterTypeService letterTypeService;
    @Resource
    private LetterOrdersService letterOrdersService;
    @Resource
    private UserService userService;
    @Resource
    private AliPayDefinedService aliPayService;
    @Resource
    private WxPayDefinedService wxPayService;
    @Resource
    private BaiDuAiHandler aiHandler;
    @Resource
    private MapperFacade mapperFacade;
    @Resource
    private RedisUtil rut;

    /**
     * 信件投递
     *
     * @param letterDTO 信件信息
     * @return {@link ApiResponse}<{@link String}>
     */
    @PostMapping("/delivery")
    @ApiOperation("信件投递")
    public ApiResponse<String> delivery(@RequestBody @Valid LetterDTO letterDTO) {
        CheckReturn<Letter> check = check(letterDTO);
        if (!check.getStatus()) {
            return ApiResponse.<String>fail(null).message(check.getMsg());
        }
        Letter letter = check.getValue();
        letter.setUserId(StpUtil.getLoginIdAsLong());
        letter.setLetterCreateTime(new Date());
        letter.setState(GlobalState.WAITING_FOR_PAYMENT.getState());
        boolean save = letterService.save(letter);
        return save ? ApiResponse.ok(letter.getLetterId().toString()).message("构建成功") : ApiResponse.<String>fail(null).message("系统异常");
    }

    /**
     * 信件删除(软删除)
     *
     * @param letterId 信件ID
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @ApiOperation("信件软删除")
    public ApiResponse<Boolean> delete(@RequestParam @Valid @NotNull(message = "信件ID不能为空") String letterId) {
        Letter letter = letterService.getById(letterId);
        if (null == letter) {
            return ApiResponse.fail(false).message("信件不存在");
        }
        if (letter.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            letter.setState(GlobalState.DELETED.getState());
            boolean update = letterService.updateById(letter);
            return update ? ApiResponse.ok(true).message("删除成功") : ApiResponse.fail(false).message("删除失败");
        }
        return ApiResponse.fail(false).message("信件不存在");
    }

    /**
     * 信件修改
     *
     * @param letterChangeDTO 信件信息
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    @ApiOperation("信件修改")
    public ApiResponse<Boolean> update(@RequestBody @Valid LetterChangeDTO letterChangeDTO) {
        // 查询信件是否存在
        Letter oldLetter = letterService.getById(letterChangeDTO.getLetterId());
        if (null == oldLetter) {
            return ApiResponse.fail(false).message("信件不存在");
        }
        // 判断是否是自己的信件
        if (!oldLetter.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            return ApiResponse.fail(false).message("信件不存在");
        }
        // 判断是否删除
        if (oldLetter.getState().equals(GlobalState.DELETED.getState())) {
            return ApiResponse.fail(false).message("信件已删除");
        }
        // 判断信件状态是否为待付款或待投递
        if (
                !oldLetter.getState().equals(GlobalState.WAITING_FOR_PAYMENT.getState()) &&
                        !oldLetter.getState().equals(GlobalState.WAITING_FOR_DELIVERY.getState())
        ) {
            return ApiResponse.fail(false).message("非法状态");
        }
        // 判断是否离投递时间小于48小时
        long betweenDay = DateUtil.between(oldLetter.getGoToTime(), new Date(), DateUnit.HOUR);
        if (betweenDay < 48) {
            return ApiResponse.fail(false).message("离投递时间小于48小时");
        }
        // TODO：修改次数
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        String key = null;
        // 判断手机号是否更改
        if (!oldLetter.getPhone().equals(letterChangeDTO.getPhone())) {
            // 校验验证码
            String code;
            if (oldLetter.getIsYourself()) {
                code = (String) rut.get(KeyType.NORMAL.getKey() + letterChangeDTO.getPhone());
                key = KeyType.NORMAL.getKey() + letterChangeDTO.getPhone();
            } else {
                // 获取当前登录用户手机号
                code = (String) rut.get(KeyType.NORMAL.getKey() + user.getPhone());
                key = KeyType.NORMAL.getKey() + user.getPhone();
            }
            if (StringUtils.isBlank(code) || !code.equals(letterChangeDTO.getSmsCode())) {
                return ApiResponse.fail(false).message("验证码错误");
            }
        }
        // 重新走内容校验 TODO：可改为MD5校验(P2)
        String checkMsg = aiHandler.letterChangeAiCheck(letterChangeDTO);
        if (StringUtils.isNotBlank(checkMsg)) {
            oldLetter.setState(GlobalState.MANUAL_REVIEW_AGAIN.getState());
            oldLetter.setAiCheckMsg(checkMsg);
        }
        // 删除验证码
        if (key != null) {
            rut.delete(key);
        }
        mapperFacade.map(letterChangeDTO, oldLetter);
        boolean update = letterService.updateById(oldLetter);
        return update ? ApiResponse.ok(true).message("修改成功") : ApiResponse.fail(false).message("修改失败");
    }

    /**
     * 信件查询(根据ID)
     *
     * @param letterId 信件ID
     * @return {@link ApiResponse}<{@link LetterVO}>
     */
    @GetMapping("/query/{letterId}")
    @ApiOperation("信件查询(根据ID)")
    public ApiResponse<LetterVO> query(@PathVariable @Valid @NotNull(message = "信件ID不能为空") String letterId) {
        Letter letter = letterService.getById(Long.parseLong(letterId));
        if (null == letter) {
            return ApiResponse.<LetterVO>fail(null).message("信件不存在");
        }
        if (!letter.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            return ApiResponse.<LetterVO>fail(null).message("信件不存在");
        }
        if (letter.getState().equals(GlobalState.DELETED.getState())) {
            return ApiResponse.<LetterVO>fail(null).message("信件不存在");
        }
        return ApiResponse.ok(mapperFacade.map(letter, LetterVO.class)).message("查询成功");
    }

    /**
     * 信件查询(根据用户)
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ApiResponse}<{@link List}<{@link LetterVO}>>
     */
    @GetMapping("/query/{page}/{size}")
    @ApiOperation("信件查询(根据用户)")
    public ApiResponse<List<LetterVO>> query(
            @PathVariable @Valid @NotNull(message = "页码不能为空") Integer page,
            @PathVariable @Valid @NotNull(message = "页数不能为空") Integer size) {
        List<Letter> letter = letterService.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<Letter>()
                        .eq(Letter::getUserId, StpUtil.getLoginIdAsLong())
                        .ne(Letter::getState, GlobalState.DELETED.getState())
                        .orderByDesc(Letter::getLetterCreateTime)
        ).getRecords();
        return ApiResponse.ok(mapperFacade.mapAsList(letter, LetterVO.class)).message("查询成功");
    }

    /**
     * 取消订单
     *
     * @param orderId 订单ID
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/cancel")
    @ApiOperation("取消订单")
    public ApiResponse<Boolean> cancel(@RequestParam @Valid @NotNull(message = "订单ID不能为空") String orderId) {
        LetterOrders letterOrders = letterOrdersService.getOne(
                new LambdaQueryWrapper<LetterOrders>()
                        .eq(LetterOrders::getOrdersSerial, orderId)
                        .eq(LetterOrders::getState, PayState.UNPAID.getValue())
                        .eq(LetterOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (null == letterOrders) {
            return ApiResponse.fail(false).message("订单不存在");
        }
        letterOrders.setState(PayState.CANCEL.getValue());
        boolean update = letterOrdersService.updateById(letterOrders);
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
        LetterOrders letterOrders = letterOrdersService.getOne(
                new LambdaQueryWrapper<LetterOrders>()
                        .eq(LetterOrders::getId, id)
                        .eq(LetterOrders::getState, PayState.UNPAID.getValue())
                        .eq(LetterOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (null == letterOrders) {
            return ApiResponse.fail(false).message("订单不存在");
        }
        letterOrders.setState(PayState.CANCEL.getValue());
        boolean update = letterOrdersService.updateById(letterOrders);
        return update ? ApiResponse.ok(true).message("取消成功") : ApiResponse.fail(false).message("取消失败");
    }

    /**
     * 获取支付URL
     *
     * @param letterId 信件ID
     * @param payType  支付类型
     * @return {@link ApiResponse}<{@link String}>
     */
    @GetMapping("/pay")
    @ApiOperation("信件支付")
    public ApiResponse<String> pay(
            @Valid @NotNull(message = "信件ID不能为空") String letterId,
            @Valid @NotNull(message = "支付类型不能为空") Integer payType) {
        String payUrl = toPay(letterId, payType);
        if (StringUtils.isBlank(payUrl)) {
            return ApiResponse.fail("支付异常");
        }
        return ApiResponse.ok(payUrl);
    }

    /**
     * 获取支付二维码
     *
     * @param letterId 信件ID
     * @param payType  支付类型
     * @return {@link byte[]}
     */
    @ApiOperation("信件支付二维码")
    @GetMapping(value = "/pay/qrcode", produces = "image/jpeg;charset=UTF-8")
    public byte[] payQrCode(
            @Valid @NotNull(message = "信件ID不能为空") String letterId,
            @Valid @NotNull(message = "支付类型不能为空") Integer payType) {
        String payUrl = toPay(letterId, payType);
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
     * 信件支付
     *
     * @param letterId 信件ID
     * @param payType  支付类型
     * @return {@link String}
     */
    private String toPay(String letterId, Integer payType) {
        if (StringUtils.isBlank(letterId)) {
            throw new GloballyUniversalException(500, "信件ID不能为空");
        }
        PayType payTypeEnum = PayType.getPayTypeEnum(payType);
        if (payTypeEnum == null) {
            throw new GloballyUniversalException(500, "支付类型错误");
        }
        Long id = Long.valueOf(letterId);
        Letter letter = letterService.getById(id);
        if (letter == null) {
            throw new GloballyUniversalException(500, "信件不存在");
        }
        if (!letter.getState().equals(GlobalState.WAITING_FOR_PAYMENT.getState())) {
            throw new GloballyUniversalException(500, "信件状态异常");
        }
        LetterOrdersBuilder build = LetterOrdersBuilder.builder()
                .letterId(letter.getLetterId())
                .ordersSerial(OrderNumberUtil.createOrderNumber(ScenarioType.LETTER))
                .date(new Date())
                .payType(payTypeEnum.getId())
                .state(PayState.UNPAID.getValue())
                .userId(StpUtil.getLoginIdAsLong())
                .build();
        boolean save = letterOrdersService.save(mapperFacade.map(build, LetterOrders.class));
        if (!save) {
            throw new GloballyUniversalException(500, "订单创建失败");
        }
        if (payType.equals(PayType.WX_PAY.getId())) {
            return wxPayService
                    .urlPath(PayCallbackUrlEnum.WX_CALLBACK_URL)
                    .getQrPay(new PayOrder("云寄信件", "云寄实体信件与服务", PayAmount.LETTER_PRICE.getAmount(), build.getOrdersSerial(), WxTransactionType.NATIVE));
        } else if (payType.equals(PayType.ALI_PAY.getId())) {
            return aliPayService
                    .urlPath(PayCallbackUrlEnum.ALI_CALLBACK_URL)
                    .getQrPay(new PayOrder("云寄信件", "云寄实体信件与服务", PayAmount.LETTER_PRICE.getAmount(), build.getOrdersSerial(), AliTransactionType.SWEEPPAY));
        } else {
            throw new GloballyUniversalException(500, "支付类型错误");
        }
    }

    /**
     * 信件支付(订单)
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
        LetterOrders orders = letterOrdersService.getOne(
                new LambdaQueryWrapper<LetterOrders>()
                        .eq(LetterOrders::getOrdersSerial, orderNumber)
                        .eq(LetterOrders::getState, PayState.UNPAID.getValue())
                        .eq(LetterOrders::getUserId, StpUtil.getLoginIdAsLong())
        );
        if (orders == null) {
            throw new GloballyUniversalException(500, "订单异常");
        }
        if (payType.equals(PayType.WX_PAY.getId())) {
            return wxPayService
                    .urlPath(PayCallbackUrlEnum.WX_CALLBACK_URL)
                    .getQrPay(new PayOrder("云寄信件", "云寄实体信件与服务", PayAmount.LETTER_PRICE.getAmount(), orders.getOrdersSerial(), WxTransactionType.NATIVE));
        }
        if (payType.equals(PayType.ALI_PAY.getId())) {
            return aliPayService
                    .urlPath(PayCallbackUrlEnum.ALI_CALLBACK_URL)
                    .getQrPay(new PayOrder("云寄信件", "云寄实体信件与服务", PayAmount.LETTER_PRICE.getAmount(), orders.getOrdersSerial(), AliTransactionType.SWEEPPAY));
        }
        throw new GloballyUniversalException(500, "未知异常");
    }

    /**
     * 校验
     *
     * @param letterDTO 信件
     */
    private CheckReturn<Letter> check(LetterDTO letterDTO) {
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        if (user.getPhone() == null) {
            return CheckReturn.fail("请先绑定手机号");
        }
        // 校验验证码
        String code;
        String key;
        if (letterDTO.getIsYourself()) {
            code = (String) rut.get(KeyType.NORMAL.getKey() + letterDTO.getPhone());
            key = KeyType.NORMAL.getKey() + user.getPhone();
        } else {
            // 获取当前登录用户手机号
            code = (String) rut.get(KeyType.NORMAL.getKey() + user.getPhone());
            key = KeyType.NORMAL.getKey() + letterDTO.getPhone();
        }
        if (StringUtils.isBlank(code) || !code.equals(letterDTO.getSmsCode())) {
            return CheckReturn.fail("验证码错误");
        }
        if (letterTypeService.getById(letterDTO.getLetterType()) == null) {
            return CheckReturn.fail("信件类型不存在");
        }
        // 删除验证码
        rut.delete(key);
        return CheckReturn.ok(mapperFacade.map(letterDTO, Letter.class));
    }
}
