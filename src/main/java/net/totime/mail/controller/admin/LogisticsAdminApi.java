/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package net.totime.mail.controller.admin;

import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.raycloud.open.sdk.api.request.KdzsLogisticsTraceSearchRequest;
import com.raycloud.open.sdk.api.request.KdzsLogisticsTraceSubscribeRequest;
import com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSearchResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.annotation.SaAdminCheckRole;
import net.totime.mail.domain.CourierOperate;
import net.totime.mail.dto.LogisticsDTO;
import net.totime.mail.entity.Letter;
import net.totime.mail.entity.Logistics;
import net.totime.mail.enums.GlobalState;
import net.totime.mail.enums.LogisticsState;
import net.totime.mail.exception.RuntimeExceptionToMsgException;
import net.totime.mail.mongo.MongoUtil;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.LetterService;
import net.totime.mail.service.LogisticsService;
import net.totime.mail.vo.CourierSubVO;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/12
 * @description 物流相关接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/admin/logistics")
@Api(tags = "[后台]物流操作接口")
@Slf4j
public class LogisticsAdminApi {
    @Resource
    private LogisticsService logisticsService;
    @Resource
    private LetterService letterService;
    @Resource
    private CourierOperate courierOperate;
    @Resource
    private MongoUtil mongoUtil;
    @Resource
    private MapperFacade mapperFacade;

    /**
     * 物流发货
     *
     * @param logisticsDTO 物流信息
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @SneakyThrows
    @PostMapping("/go")
    @SaAdminCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @ApiOperation(value = "物流发货", notes = "物流发货接口")
    public ApiResponse<String> go(@RequestBody @Valid LogisticsDTO logisticsDTO) {
        //查询DB是否存在
        Logistics l = logisticsService.getOne(
                new LambdaQueryWrapper<>(Logistics.class)
                        .eq(Logistics::getLogisticsId, logisticsDTO.getLogisticsId())
        );
        if (l != null) {
            return ApiResponse.fail("物流信息已存在");
        }
        // 校验信件是否存在
        Letter letter = letterService.getById(logisticsDTO.getLetterId());
        if (letter == null) {
            return ApiResponse.fail("信件不存在");
        }
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
            log.error("订阅物流失败，物流信息：{}", response);
            throw new RuntimeExceptionToMsgException("订阅物流信息失败", "物流");
        }
        //物流入库
        boolean b = mongoUtil.insertDocument("logistics", response);
        if (!b) {
            log.error("物流入库失败，物流信息：{}", response);
            throw new RuntimeExceptionToMsgException("物流入库失败", "物流入库：go");
        }
        Logistics logistics = mapperFacade.map(logisticsDTO, Logistics.class);
        logistics.setStatus(LogisticsState.WAIT_ACCEPT.getCode());
        logistics.setCreateTime(new Date());
        logistics.setUpdateTime(new Date());
        boolean save = logisticsService.save(logistics);
        if (!save) {
            log.error("保存失败，物流信息：{}", logistics);
            throw new RuntimeExceptionToMsgException("物流信息持久化保存失败", "物流");
        }
        //更新信件状态
        letter.setState(GlobalState.DELIVERED.getState());
        boolean update = letterService.updateById(letter);
        if (!update) {
            log.error("更新信件状态失败，信件信息：{}", letter);
            throw new RuntimeExceptionToMsgException("信件发货更新状态失败", "信件");
        }
        return ApiResponse.ok("入单成功");
    }

    /**
     * 物流清空重发
     *
     * @param logisticsDTO 物流信息
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    @SneakyThrows
    @PostMapping("/reGo")
    @SaAdminCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @ApiOperation(value = "物流清空重发", notes = "物流清空重发接口")
    public ApiResponse<String> reGo(@RequestBody @Valid LogisticsDTO logisticsDTO) {
        //查询DB是否存在
        Logistics l = logisticsService.getOne(
                new LambdaQueryWrapper<>(Logistics.class)
                        .eq(Logistics::getLogisticsId, logisticsDTO.getLogisticsId())
        );
        if (l == null) {
            return ApiResponse.fail("物流信息不存在");
        }
        String logisticsId = l.getLogisticsId();
        String logisticsCode = l.getLogisticsCode();
        // 删除DB物流信息
        logisticsService.remove(
                new LambdaQueryWrapper<>(Logistics.class)
                        .eq(Logistics::getLetterId, logisticsDTO.getLetterId())
        );
        // 删除MongoDB物流信息
        Query query = new Query();
        query.addCriteria(Criteria.where("mailNo").is(logisticsId));
        query.addCriteria(Criteria.where("cpCode").is(logisticsCode));
        mongoUtil.deleteDocument("logistics", query);
        mongoUtil.deleteDocument("courierSub", query);
        // 校验信件是否存在
        Letter letter = letterService.getById(logisticsDTO.getLetterId());
        if (letter == null) {
            return ApiResponse.fail("信件不存在");
        }
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
            log.error("订阅物流失败，物流信息：{}", response);
            throw new RuntimeExceptionToMsgException("订阅物流信息失败", "物流");
        }
        //物流入库
        boolean b = mongoUtil.insertDocument("logistics", response);
        if (!b) {
            log.error("物流入库失败，物流信息：{}", response);
            throw new RuntimeExceptionToMsgException("物流入库失败", "物流入库：go");
        }
        Logistics logistics = mapperFacade.map(logisticsDTO, Logistics.class);
        logistics.setStatus(LogisticsState.WAIT_ACCEPT.getCode());
        logistics.setCreateTime(new Date());
        logistics.setUpdateTime(new Date());
        boolean save = logisticsService.save(logistics);
        if (!save) {
            log.error("保存失败，物流信息：{}", logistics);
            throw new RuntimeExceptionToMsgException("物流信息持久化保存失败", "物流");
        }
        return ApiResponse.ok("入单成功");
    }
}
