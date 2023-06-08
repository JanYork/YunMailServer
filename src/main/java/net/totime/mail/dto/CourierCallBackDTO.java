/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/11
 * @description 快递助手推送回调
 * @since 1.0.0
 */
@Data
@Document(collection = "courierSub")
public class CourierCallBackDTO {
    /**
     * 快递公司CODE 详见：快递公司编码对照表
     */
    private String cpCode;
    /**
     * 物流单号
     */
    private String mailNo;
    /**
     * 完整的物流轨迹信息
     */
    private List<LogisticsFullTraceList> logisticsFullTraceList;
    /**
     * 当前最新物流信息描述内容
     */
    private String logisticsStatusDesc;
    /**
     * 当前最新物流状态 详见：物流状态编码对照表
     */
    private String logisticsStatus;

    /**
     * @author JanYork
     * @version 1.0.0
     * @date 2023/05/11
     * @description 物流轨迹信息List
     * @since 1.0.0
     */
    @Data
    private static class LogisticsFullTraceList {
        /**
         * 路由节点所在地区行政编码
         */
        private String areaCode;
        /**
         * 路由节点所在地区
         */
        private String areaName;
        /**
         * 物流子状态 详见：物流状态编码对照表
         */
        private String subLogisticsStatus;
        /**
         * 物流状态 详见：物流状态编码对照表
         */
        private String logisticsStatus;
        /**
         * 物流变更时间
         */
        private String time;
        /**
         * 物流信息描述内容
         */
        private String desc;
    }
}
