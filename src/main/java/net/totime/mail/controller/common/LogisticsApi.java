/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package net.totime.mail.controller.common;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSearchResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.annotation.SaAdminCheckRole;
import net.totime.mail.entity.Letter;
import net.totime.mail.entity.Logistics;
import net.totime.mail.enums.LogisticsState;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.mongo.MongoUtil;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.security.StpAdminUtil;
import net.totime.mail.service.LetterService;
import net.totime.mail.service.LogisticsService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/12
 * @description 物流相关接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/common/logistics")
@Api(tags = "[公共]物流接口")
@Slf4j
public class LogisticsApi {
    @Resource
    private LogisticsService logisticsService;
    @Resource
    private LetterService letterService;
    @Resource
    private MongoUtil mongoUtil;

    /**
     * 物流查询
     *
     * @param mailNo 快递单号
     * @param cpCode 快递公司编码
     * @return {@link ApiResponse}<{@link KdzsLogisticsTraceSearchResponse.LogisticsTraceBean}>
     */
    @GetMapping("/search")
    @SaAdminCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @ApiOperation("物流查询")
    public ApiResponse<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean> search(String mailNo, String cpCode) {
        Logistics logisticsDb = logisticsService.getOne(
                new LambdaQueryWrapper<>(Logistics.class)
                        .eq(Logistics::getLogisticsId, mailNo)
                        .eq(Logistics::getLogisticsCode, cpCode)
        );
        if (logisticsDb == null) {
            return ApiResponse.<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean>fail(null).message("未查询到物流信息");
        }
        Letter letter = letterService.getById(logisticsDb.getLetterId());
        if (letter == null) {
            return ApiResponse.<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean>fail(null).message("未查询到物流信息");
        }
        // 判断信件是否是本人
        Object id = StpAdminUtil.getLoginIdDefaultNull();
        if (id == null) {
            Long userId = StpUtil.getLoginIdAsLong();
            if (!userId.equals(letter.getUserId())) {
                throw new GloballyUniversalException(500, "无权限查看");
            }
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("mailNo").is(mailNo));
        query.addCriteria(Criteria.where("cpCode").is(cpCode));
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean logistics = mongoUtil.findDocument("logistics", KdzsLogisticsTraceSearchResponse.LogisticsTraceBean.class, query);
        if (logistics == null) {
            return ApiResponse.<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean>fail(null).message("未查询到物流信息");
        }
        return ApiResponse.ok(logistics);
    }

    /**
     * 物流查询
     *
     * @param letterId 信件ID
     * @return {@link ApiResponse}<{@link KdzsLogisticsTraceSearchResponse.LogisticsTraceBean}>
     */
    @GetMapping("/search/letter")
    @ApiOperation("物流查询，根据信件ID")
    public ApiResponse<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean> searchByLetterId(String letterId) {
        if (letterId == null) {
            throw new GloballyUniversalException(500, "信件ID不能为空");
        }
        // 查询信件信息
        Letter letter = letterService.getById(letterId);
        if (letter == null) {
            throw new GloballyUniversalException(500, "信件不存在");
        }
        // 判断信件是否是本人
        Object id = StpAdminUtil.getLoginIdDefaultNull();
        if (id == null) {
            Long userId = StpUtil.getLoginIdAsLong();
            if (!userId.equals(letter.getUserId())) {
                throw new GloballyUniversalException(500, "无权限查看");
            }
        }
        Logistics logisticsDb = logisticsService.getOne(
                new LambdaQueryWrapper<>(Logistics.class)
                        .eq(Logistics::getLetterId, letterId)
        );
        if (logisticsDb == null) {
            return ApiResponse.<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean>fail(null).message("未查询到物流信息");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("mailNo").is(logisticsDb.getLogisticsId()));
        query.addCriteria(Criteria.where("cpCode").is(logisticsDb.getLogisticsCode()));
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean logistics = mongoUtil.findDocument("logistics", KdzsLogisticsTraceSearchResponse.LogisticsTraceBean.class, query);
        if (logistics == null) {
            return ApiResponse.<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean>fail(null).message("未查询到物流信息");
        }
        return ApiResponse.ok(logistics);
    }

    /**
     * 物流类型
     *
     * @return {@link Map}<{@link Integer}, {@link String}>
     */
    @GetMapping("/type")
    @SaIgnore
    @ApiOperation("物流类型信息")
    public ApiResponse<Map<Integer, String>> logisticsType() {
        return ApiResponse.ok(LogisticsState.getMap());
    }
}
