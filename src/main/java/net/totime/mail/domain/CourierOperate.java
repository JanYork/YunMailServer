/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain;

import cn.hutool.crypto.digest.MD5;
import com.raycloud.open.sdk.api.KdzsClient;
import com.raycloud.open.sdk.api.base.exception.ApiRuleException;
import com.raycloud.open.sdk.api.request.KdzsLogisticsTraceSearchRequest;
import com.raycloud.open.sdk.api.request.KdzsLogisticsTraceSubscribeRequest;
import com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSearchResponse;
import com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSubscribeResponse;
import net.totime.mail.vo.CourierSubVO;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/11
 * @description 快递助手API操作服务
 * @since 1.0.0
 */
@Service
public class CourierOperate {
    @Resource
    private KdzsClient kdzsClient;

    /**
     * 查询快递轨迹
     *
     * @return {@link KdzsLogisticsTraceSearchResponse.LogisticsTraceBean}
     * @throws ApiRuleException 规则错误
     */
    public KdzsLogisticsTraceSearchResponse.LogisticsTraceBean checkCourierTrack(KdzsLogisticsTraceSearchRequest request) throws ApiRuleException {
        KdzsLogisticsTraceSearchResponse response = kdzsClient.execute(request);
        boolean success = response.isSuccess();
        if (!success) {
            return null;
        }
        return response.getLogisticsTrace();
    }

    /**
     * 订阅快递轨迹
     *
     * @param subRequest 子请求
     * @return {@link KdzsLogisticsTraceSubscribeResponse}
     * @throws ApiRuleException 规则错误
     */
    public CourierSubVO subscribeCourierTrack(KdzsLogisticsTraceSubscribeRequest subRequest) throws ApiRuleException {
        KdzsLogisticsTraceSubscribeResponse response = kdzsClient.execute(subRequest);
        boolean success = response.isSuccess();
        if (!success) {
            return null;
        }
        return CourierSubVO.builder().traceId(response.getTraceId()).isSub(response.getParams().get("isSub")).build();
    }

    /**
     * 快递推送参数验签
     *
     * @param request 请求
     * @return {@link String}
     * @throws IOException IO异常
     */
    public String checkSign(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String json = jsonBuilder.toString();
        String signContent = kdzsClient.getAppSecret() + json + kdzsClient.getAppSecret();
        String b64 = Base64.encodeBase64String(MD5.create().digestHex(signContent).getBytes());
        String sign = request.getHeader("dataDigest");
        if (!sign.equals(b64)) {
            return null;
        }
        return json;
    }
}
