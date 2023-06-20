/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.v3.bean.WxTransactionType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.annotation.RateLimiter;
import net.totime.mail.dto.SponsorDTO;
import net.totime.mail.entity.Sponsor;
import net.totime.mail.entity.User;
import net.totime.mail.enums.PayCallbackUrlEnum;
import net.totime.mail.enums.PayState;
import net.totime.mail.enums.PayType;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.handler.AliPayMessageHandler;
import net.totime.mail.handler.WxV3PayMessageHandler;
import net.totime.mail.pay.AliPayDefinedService;
import net.totime.mail.pay.WxPayDefinedService;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.SponsorService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.OrderNumberUtil;
import net.totime.mail.util.QrUtil;
import net.totime.mail.vo.SponsorInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/13
 * @description 赞助接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user/sponsor")
@Api(tags = "[用户]云寄赞助接口")
public class SponsorApi {
    @Resource
    private AliPayDefinedService aliPay;
    @Resource
    private WxPayDefinedService wxPay;
    @Resource
    private SponsorService sponsorService;
    @Resource
    private UserService userService;
    @Resource
    private MapperFacade mapperFacade;

    /**
     * 支付宝赞助
     *
     * @param sponsorDTO 赞助信息
     * @return {@link byte[]} 支付宝支付二维码
     */
    @SneakyThrows
    @RateLimiter(count = 3)
    @GetMapping(value = "/aliPay", produces = "image/jpeg;charset=UTF-8")
    @ApiOperation(value = "支付宝赞助")
    public byte[] aliPaySponsor(SponsorDTO sponsorDTO) {
        Long sponsorOrderNumber = OrderNumberUtil.createSponsorOrderNumber(PayType.ALI_PAY);
        Sponsor sponsor = new Sponsor();
        sponsor.setSponsorSay(sponsorDTO.getSponsorSay());
        sponsor.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        sponsor.setCreateTime(new Date());
        sponsor.setState(PayState.UNPAID.getValue());
        sponsor.setId(sponsorOrderNumber);
        sponsor.setPayType(PayType.ALI_PAY);
        sponsor.setSponsorAmount(sponsorDTO.getSponsorAmount());
        if (!sponsorService.save(sponsor)) {
            throw new GloballyUniversalException(500, "赞助订单创建失败");
        }
        BigDecimal amount = sponsorDTO.getSponsorAmount();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(aliPay
                .urlPath(PayCallbackUrlEnum.ALI_DONATE_CALLBACK_URL)
                .backHandler(new AliPayMessageHandler.SponsorHandler())
                .genQrPay(new PayOrder("云寄赞助", "感谢对云寄服务的支持", amount, String.valueOf(sponsorOrderNumber), AliTransactionType.SWEEPPAY)), "JPEG", bos);
        return bos.toByteArray();
    }

    /**
     * 微信赞助
     *
     * @param sponsorDTO 赞助信息
     * @return {@link byte[]} 微信支付二维码
     */
    @SneakyThrows
    @RateLimiter(count = 3)
    @GetMapping(value = "/wxPay", produces = "image/jpeg;charset=UTF-8")
    @ApiOperation(value = "微信赞助")
    public byte[] wxPaySponsor(SponsorDTO sponsorDTO) {
        Long sponsorOrderNumber = OrderNumberUtil.createSponsorOrderNumber(PayType.WX_PAY);
        Sponsor sponsor = new Sponsor();
        sponsor.setSponsorSay(sponsorDTO.getSponsorSay());
        sponsor.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        sponsor.setCreateTime(new Date());
        sponsor.setState(PayState.UNPAID.getValue());
        sponsor.setId(sponsorOrderNumber);
        sponsor.setPayType(PayType.WX_PAY);
        sponsor.setSponsorAmount(sponsorDTO.getSponsorAmount());
        if (!sponsorService.save(sponsor)) {
            throw new GloballyUniversalException(500, "赞助订单创建失败");
        }
        BigDecimal amount = sponsorDTO.getSponsorAmount();
        String qrUrl = wxPay
                .urlPath(PayCallbackUrlEnum.WX_DONATE_CALLBACK_URL)
                .backHandler(new WxV3PayMessageHandler.SponsorHandler())
                .getQrPay(new PayOrder("感谢对云寄服务的支持", "感谢对云寄服务的支持", amount, String.valueOf(sponsorOrderNumber), WxTransactionType.NATIVE));
        return QrUtil.getByteArrayWithLogo(qrUrl);
    }

    /**
     * 获取赞助信息-分页
     *
     * @param page 页码
     * @return {@link List}<{@link SponsorInfoVO}>
     */
    @GetMapping("/query/{page}")
    @RateLimiter(count = 10)
    @ApiOperation(value = "获取赞助信息-分页", notes = "获取赞助信息，此接口不需要登录")
    @SaIgnore
    public ApiResponse<List<SponsorInfoVO>> querySponsor(@PathVariable Integer page, Integer size) {
        if (size == null) {
            size = 10;
        }
        if (page == null) {
            page = 1;
        }
        List<SponsorInfoVO> collect = sponsorService.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<>(new Sponsor())
                        .eq(Sponsor::getState, PayState.PAID.getValue())
                        .orderByDesc(Sponsor::getCreateTime)
        ).getRecords().stream().map(sponsor -> {
            SponsorInfoVO sponsorInfoVO = mapperFacade.map(sponsor, SponsorInfoVO.class);
            // 获取用户信息
            User user = userService.getById(sponsor.getUserId());
            sponsorInfoVO.setUserNickname(user.getNickName());
            sponsorInfoVO.setUserAvatar(user.getAvatar());
            return sponsorInfoVO;
        }).collect(Collectors.toList());
        return ApiResponse.ok(collect);
    }
}
