/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.open;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.raycloud.open.sdk.api.base.exception.ApiRuleException;
import com.raycloud.open.sdk.api.request.KdzsLogisticsTraceSearchRequest;
import com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSearchResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.domain.CourierOperate;
import net.totime.mail.dto.CourierCallBackDTO;
import net.totime.mail.entity.Letter;
import net.totime.mail.entity.Logistics;
import net.totime.mail.enums.GlobalState;
import net.totime.mail.enums.LogisticsState;
import net.totime.mail.exception.RuntimeExceptionToMsgException;
import net.totime.mail.mongo.MongoUtil;
import net.totime.mail.service.LetterService;
import net.totime.mail.service.LogisticsService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/25
 * @description 物流订阅回调接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/open/v1/logistics")
@Api(tags = "[回调]物流信息回调接口")
@Slf4j
public class LogisticsCallBackApi {
    @Resource
    private LogisticsService logisticsService;
    @Resource
    private LetterService letterService;
    @Resource
    private CourierOperate courierOperate;
    @Resource
    private MongoUtil mongoUtil;

    /**
     * 物流订阅回调
     *
     * @param request 请求
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @SneakyThrows
    @PostMapping("/callback")
    @ApiOperation(value = "物流订阅回调", notes = "物流订阅回调，存在验签，请勿随意调用")
    public HashMap<String, Object> callBack(HttpServletRequest request) {
        //验签回调
        String str = courierOperate.checkSign(request);
        if (!StringUtils.hasText(str)) {
            return new HashMap<>(4) {
                private static final long serialVersionUID = -1678563287372612414L;

                {
                    put("code", 100);
                    put("msg", "接收失败");
                }
            };
        }
        CourierCallBackDTO courierCallBack = JSON.parseObject(str, CourierCallBackDTO.class);
        //刷新物流
        boolean u = updateLogistics(courierCallBack.getMailNo(), courierCallBack.getCpCode());
        if (!u) {
            log.error("更新物流信息失败");
            throw new RuntimeExceptionToMsgException("更新物流信息失败", "物流刷新");
        }
        //查询文档是否存在
        Query query = new Query();
        query.addCriteria(Criteria.where("mailNo").is(courierCallBack.getMailNo()));
        query.addCriteria(Criteria.where("cpCode").is(courierCallBack.getCpCode()));
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean logistics = mongoUtil.findDocument("courierSub", KdzsLogisticsTraceSearchResponse.LogisticsTraceBean.class, query);
        if (logistics == null) {
            boolean r = mongoUtil.insertDocument(courierCallBack);
            if (!r) {
                log.error("物流订阅回调信息写入失败");
                throw new RuntimeExceptionToMsgException("物流订阅回调信息写入失败", "物流回调");
            }
        }
        //更新物流订阅
        boolean b = mongoUtil.updateDocument("courierSub", courierCallBack, query);
        if (!b) {
            log.error("物流订阅回调信息更新失败");
            throw new RuntimeExceptionToMsgException("更新失败");
        }
        return new HashMap<>(4) {

            private static final long serialVersionUID = -5980362810437982932L;

            {
                put("code", 200);
            }
        };
    }

    /**
     * 更新物流
     *
     * @param mailNo 快递单号
     * @param cpCode 快递公司编码
     * @return boolean 是否更新成功
     * @throws ApiRuleException api异常
     * @description 更新物流信息，收到订阅后再去查询物流信息是因为，快递助手一个订单无限制查询，不重复收费，嘿嘿嘿
     */
    private boolean updateLogistics(String mailNo, String cpCode) throws ApiRuleException {
        Query query = new Query();
        query.addCriteria(Criteria.where("mailNo").is(mailNo));
        query.addCriteria(Criteria.where("cpCode").is(cpCode));
        //查文档数据
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean logistics = mongoUtil.findDocument("logistics", KdzsLogisticsTraceSearchResponse.LogisticsTraceBean.class, query);
        if (logistics == null) {
            log.error("物流信息不存在？？？");
            throw new RuntimeExceptionToMsgException("更新发现？物流信息不存在？？？");
        }
        KdzsLogisticsTraceSearchRequest request = new KdzsLogisticsTraceSearchRequest();
        request.setMailNo(mailNo);
        request.setCpCode(cpCode);
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean response = courierOperate.checkCourierTrack(request);
        boolean r = mongoUtil.updateDocument("logistics", response, query);
        // 更新DB
        Logistics logisticsDb = logisticsService.getOne(
                new LambdaQueryWrapper<>(Logistics.class)
                        .eq(Logistics::getLogisticsId, mailNo)
                        .eq(Logistics::getLogisticsCode, cpCode)
        );
        if (logisticsDb == null) {
            throw new RuntimeExceptionToMsgException("DB物流信息不存在", "物流DB更新");
        }
        // 查询物流状态，是否SIGN
        String logisticsStatus = response.getLogisticsStatus();
        if (StringUtils.hasText(logisticsStatus)) {
            // TODO：详细的物流状态(P1)
            if ("SIGN".equals(logisticsStatus)) {
                logisticsDb.setStatus(LogisticsState.DELIVERING_SUCCESS.getCode());
            } else {
                logisticsDb.setStatus(LogisticsState.TRANSPORT.getCode());
            }
        }
        logisticsDb.setCreateTime(new Date());
        logisticsDb.setUpdateTime(new Date());
        boolean save = logisticsService.updateById(logisticsDb);
        if (logisticsDb.getStatus().equals(LogisticsState.DELIVERING_SUCCESS.getCode())) {
            // 信件状态更新
            Letter letter = letterService.getOne(
                    new LambdaQueryWrapper<>(Letter.class)
                            .eq(Letter::getLetterId, logisticsDb.getLetterId())
            );
            if (letter == null) {
                throw new RuntimeExceptionToMsgException("信件查库发现信息不存在", "信件状态");
            }
            letter.setState(GlobalState.COMPLETED.getState());
            boolean update = letterService.updateById(letter);
            if (!update) {
                log.error("信件状态更新失败，信件信息：{}", letter);
                throw new RuntimeExceptionToMsgException("信件物流状态更新失败", "信件状态");
            }
        }
        if (!save) {
            log.error("更新DB失败，物流信息：{}", logistics);
            throw new RuntimeExceptionToMsgException("物流信息持久化保存失败", "物流");
        }
        return r;
    }
}
