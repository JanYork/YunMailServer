/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/18
 * @description 百度AI返回
 * @since 1.0.0
 */
@Data
public class BaiDuAiBack {
    /**
     * 请求唯一标识码
     */
    private Long logId;
    /**
     * 错误码
     */
    private Long errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 审核结果
     */
    private String conclusion;
    /**
     * 审核结果类型，可取值1.合规，2.不合规，3.疑似，4.审核失败
     */
    private Integer conclusionType;
    /**
     * 审核明细项
     */
    private List<Data> data;

    @lombok.Data
    public static class Data {
        /**
         * 审核主类型，11：百度官方违禁词库、12：文本反作弊、13:自定义文本黑名单、14:自定义文本白名单
         */
        private Integer type;
        /**
         * 审核子类型
         */
        private Integer subType;
        /**
         * 审核结果
         */
        private String conclusion;
        /**
         * 审核结果类型，可取值1.合规，2.不合规，3.疑似，4.审核失败
         */
        private Integer conclusionType;
        /**
         * 不合规项描述信息
         */
        private String msg;
        /**
         * 审核明细项
         */
//        private List<Hits> hits;
    }

    @lombok.Data
    public static class Hits {
        /**
         * 命中的文本位置信息
         */
        private List<WordHitPositions> wordHitPositions;
        /**
         * 命中的模型名称
         */
        private String datasetName;
        /**
         * 命中的模型类型
         */
        private List<Integer> modelHitPositions;
        /**
         * 命中的文本关键词
         */
        private List<String> words;
        /**
         * 概率
         */
        private Integer probability;
    }

    @lombok.Data
    public static class WordHitPositions {
        /**
         * 命中的文本位置信息
         */
        private List<List<Integer>> positions;
        /**
         * 命中的模型名称
         */
        private String label;
        /**
         * 命中的模型类型
         */
        private String keyword;
    }

    /**
     * 检查是否合规
     *
     * @return boolean
     */
    public boolean isCompliance() {
        return conclusionType == 1;
    }
}
