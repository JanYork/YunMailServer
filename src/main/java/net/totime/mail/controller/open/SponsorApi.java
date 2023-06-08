/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.open;

import cn.dev33.satoken.stp.StpUtil;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.v3.api.WxPayService;
import com.egzosn.pay.wx.v3.bean.WxTransactionType;
import lombok.SneakyThrows;
import net.totime.mail.annotation.RateLimiter;
import net.totime.mail.domain.sponsor.SponsorOperateService;
import net.totime.mail.dto.SponsorDTO;
import net.totime.mail.entity.Sponsor;
import net.totime.mail.enums.PayState;
import net.totime.mail.enums.PayType;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.util.OrderNumberUtil;
import net.totime.mail.util.QrUtil;
import net.totime.mail.vo.SponsorInfoVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/13
 * @description 赞助接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/sponsor")
public class SponsorApi {
    @Resource
    private AliPayService aliPay;
    @Resource
    private WxPayService wxPay;
    @Resource
    private SponsorOperateService sos;
    private static final Integer SIZE = 5;

    /**
     * 支付宝赞助
     *
     * @param sponsorDTO 赞助信息
     * @return {@link byte[]} 支付宝支付二维码
     */
    @SneakyThrows
    @RateLimiter(count = 3)
    @RequestMapping(value = "/aliPay", produces = "image/jpeg;charset=UTF-8")
    public byte[] aliPaySponsor(SponsorDTO sponsorDTO) {
        Long sponsorOrderNumber = OrderNumberUtil.createSponsorOrderNumber(PayType.ALI_PAY);
        Sponsor sponsor = new Sponsor();
        sponsor.setSponsorSay(sponsorDTO.getSponsorSay());
        sponsor.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        sponsor.setCreateTime(new Date());
        sponsor.setState(PayState.UNPAID.getValue());
        sponsor.setId(sponsorOrderNumber);
        sponsor.setPayType(PayType.ALI_PAY);
        if (!sos.createSponsor(sponsor)) {
            throw new GeneralSecurityException("赞助订单创建失败");
        }
        BigDecimal amount = sponsorDTO.getSponsorAmount();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(aliPay.genQrPay(new PayOrder("云寄赞助", "感谢对云寄服务的支持", amount, String.valueOf(sponsorOrderNumber), AliTransactionType.SWEEPPAY)), "JPEG", bos);
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
    @RequestMapping(value = "/wxPay", produces = "image/jpeg;charset=UTF-8")
    public byte[] wxPaySponsor(SponsorDTO sponsorDTO) {
        Long sponsorOrderNumber = OrderNumberUtil.createSponsorOrderNumber(PayType.WX_PAY);
        Sponsor sponsor = new Sponsor();
        sponsor.setSponsorSay(sponsorDTO.getSponsorSay());
        sponsor.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        sponsor.setCreateTime(new Date());
        sponsor.setState(PayState.UNPAID.getValue());
        sponsor.setId(sponsorOrderNumber);
        sponsor.setPayType(PayType.WX_PAY);
        if (!sos.createSponsor(sponsor)) {
            throw new GeneralSecurityException("赞助订单创建失败");
        }
        BigDecimal amount = sponsorDTO.getSponsorAmount();
        String qrUrl = wxPay.getQrPay(new PayOrder("感谢对云寄服务的支持", "感谢对云寄服务的支持", amount, String.valueOf(sponsorOrderNumber), WxTransactionType.NATIVE));
        return QrUtil.getByteArrayWithLogo(qrUrl);
    }

    /**
     * 获取赞助信息-分页
     *
     * @param page 页码
     * @return {@link List}<{@link SponsorInfoVO}>
     */
    @RequestMapping("/query/{page}")
    public ApiResponse<List<SponsorInfoVO>> querySponsor(@PathVariable Integer page) {
        return ApiResponse.ok(sos.querySponsor(page, SIZE));
    }
}
