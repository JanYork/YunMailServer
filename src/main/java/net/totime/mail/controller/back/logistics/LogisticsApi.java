/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.back.logistics;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.raycloud.open.sdk.api.base.exception.ApiRuleException;
import com.raycloud.open.sdk.api.request.KdzsLogisticsTraceSearchRequest;
import com.raycloud.open.sdk.api.request.KdzsLogisticsTraceSubscribeRequest;
import com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSearchResponse;
import lombok.SneakyThrows;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.domain.courier.CourierOperateService;
import net.totime.mail.dto.CourierCallBackDTO;
import net.totime.mail.dto.LogisticsDTO;
import net.totime.mail.entity.back.Logistics;
import net.totime.mail.enums.LogisticsState;
import net.totime.mail.mongo.MongoUtil;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.LogisticsService;
import net.totime.mail.vo.CourierSubVO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/12
 * @description 物流相关接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/logistics")
public class LogisticsApi {
    @Resource
    private LogisticsService logisticsService;
    @Resource
    private CourierOperateService courierOperate;
    @Resource
    private MongoUtil mongoUtil;
    @Resource
    private MapperFacade mapperFacade;

    /**
     * 物流查询
     *
     * @param mailNo 快递单号
     * @param cpCode 快递公司编码
     * @return {@link HashMap}<{@link String}, {@link Object}>
     * TODO：禁用
     */
    @SneakyThrows
    @GetMapping("/query")
    public ApiResponse<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean> query(String mailNo, String cpCode) {
        KdzsLogisticsTraceSearchRequest request = new KdzsLogisticsTraceSearchRequest();
        request.setMailNo(mailNo);
        request.setCpCode(cpCode);
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean response = courierOperate.checkCourierTrack(request);
        //TODO：更新
        boolean b = mongoUtil.insertDocument("logistics", response);
        if (!b) {
            throw new RuntimeException("插入失败");
        }
        if (response == null) {
            return ApiResponse.fail(null);
        }
        return ApiResponse.ok(response);
    }

    /**
     * 物流查询
     *
     * @param mailNo 快递单号
     * @param cpCode 快递公司编码
     * @return {@link ApiResponse}<{@link KdzsLogisticsTraceSearchResponse.LogisticsTraceBean}>
     */
    @GetMapping("/search")
    public ApiResponse<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean> search(String mailNo, String cpCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mailNo").is(mailNo));
        query.addCriteria(Criteria.where("cpCode").is(cpCode));
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean logistics = mongoUtil.findDocument("logistics", KdzsLogisticsTraceSearchResponse.LogisticsTraceBean.class, query);
        if (logistics == null) {
            return ApiResponse.fail(null);
        }
        return ApiResponse.ok(logistics);
    }


    /**
     * 物流订阅回调
     *
     * @param request 请求
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @SneakyThrows
    @PostMapping("/callback")
    public HashMap<String, Object> callBack(HttpServletRequest request) {
        //验签回调
        String str = courierOperate.checkSign(request);
        if (!StringUtils.hasText(str)) {
            return new HashMap<String, Object>(8) {
                private static final long serialVersionUID = -1678563287372612414L;

                {
                    put("code", 100);
                    put("msg", "接收失败");
                }
            };
        }
        CourierCallBackDTO courierCallBack = JSON.parseObject(str, CourierCallBackDTO.class);
        //查询文档是否存在
        Query query = new Query();
        query.addCriteria(Criteria.where("mailNo").is(courierCallBack.getMailNo()));
        query.addCriteria(Criteria.where("cpCode").is(courierCallBack.getCpCode()));
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean logistics = mongoUtil.findDocument("courierSub", KdzsLogisticsTraceSearchResponse.LogisticsTraceBean.class, query);
        if (logistics == null) {
            boolean r = mongoUtil.insertDocument(courierCallBack);
            if (!r) {
                throw new RuntimeException("插入失败");
            }
        }
        //更新物流订阅
        boolean b = mongoUtil.updateDocument("courierSub", courierCallBack, query);
        if (!b) {
            throw new RuntimeException("更新失败");
        }
        //刷新物流
        boolean u = updateLogistics(courierCallBack.getMailNo(), courierCallBack.getCpCode());
        if (!u) {
            throw new RuntimeException("更新物流信息失败");
        }
        return new HashMap<String, Object>(4) {

            private static final long serialVersionUID = -5980362810437982932L;

            {
                put("code", 200);
            }
        };
    }

    /**
     * 物流发货
     *
     * @param logisticsDTO 物流信息
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @SneakyThrows
    @PostMapping("/go")
    public ApiResponse<String> go(@RequestBody LogisticsDTO logisticsDTO) {
        //查询DB是否存在
        Logistics l = logisticsService.getOne(
                new QueryWrapper<Logistics>()
                        .eq("logistics_id", logisticsDTO.getLogisticsId())
        );
        if (l != null) {
            return ApiResponse.fail("物流信息已存在");
        }
        //TODO：校验信件
        //物流查单
        KdzsLogisticsTraceSearchRequest request = new KdzsLogisticsTraceSearchRequest();
        request.setMailNo(logisticsDTO.getLogisticsId());
        request.setCpCode(logisticsDTO.getLogisticsCode());
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean response = courierOperate.checkCourierTrack(request);
        if (response.getLogisticsStatus() == null) {
            return ApiResponse.fail("物流信息不存在");
        }
        //订阅物流
        KdzsLogisticsTraceSubscribeRequest subscribeRequest = new KdzsLogisticsTraceSubscribeRequest();
        subscribeRequest.setMailNo(logisticsDTO.getLogisticsId());
        subscribeRequest.setCpCode(logisticsDTO.getLogisticsCode());
        CourierSubVO courierSubVO = courierOperate.subscribeCourierTrack(subscribeRequest);
        if (courierSubVO == null) {
            return ApiResponse.fail("订阅失败");
        }
        //物流入库
        boolean b = mongoUtil.insertDocument("logistics", response);
        if (!b) {
            //TODO：报错邮件通知
            throw new RuntimeException("插入失败");
        }
        Logistics logistics = mapperFacade.map(logisticsDTO, Logistics.class);
        logistics.setStatus(LogisticsState.WAIT_ACCEPT.getCode());
        logistics.setCreateTime(new Date());
        logistics.setUpdateTime(new Date());
        boolean save = logisticsService.save(logistics);
        if (!save) {
            return ApiResponse.fail("保存失败");
        }
        return ApiResponse.ok("入单成功");
    }

    /**
     * 物流类型
     *
     * @return {@link Map}<{@link Integer}, {@link String}>
     */
    @GetMapping("/type")
    public ApiResponse<Map<Integer, String>> logisticsType() {
        return ApiResponse.ok(LogisticsState.getMap());
    }


    /**
     * 更新物流
     *
     * @param mailNo 快递单号
     * @param cpCode 快递公司编码
     * @return boolean 是否更新成功
     * @throws ApiRuleException api异常
     */
    private boolean updateLogistics(String mailNo, String cpCode) throws ApiRuleException {
        Query query = new Query();
        query.addCriteria(Criteria.where("mailNo").is(mailNo));
        query.addCriteria(Criteria.where("cpCode").is(cpCode));
        //查文档数据
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean logistics = mongoUtil.findDocument("logistics", KdzsLogisticsTraceSearchResponse.LogisticsTraceBean.class, query);
        if (logistics == null) {
            return false;
        }
        KdzsLogisticsTraceSearchRequest request = new KdzsLogisticsTraceSearchRequest();
        request.setMailNo(mailNo);
        request.setCpCode(cpCode);
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean response = courierOperate.checkCourierTrack(request);
        return mongoUtil.updateDocument("logistics", response, query);
    }
}
