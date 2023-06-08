/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * 此版权个人开发者保留最终归属权与解释权，非版权所有者授权禁止商用与演绎.
 */

package net.totime.mail.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/02
 * @description 分页实体
 * @since 1.0.0
 */
@Data
@Builder
@ApiModel(value = "分页信息对象")
public class Pagination <T> {
        /**
        * 当前页
        */
        @ApiModelProperty(value = "当前页")
        private Integer page;
        /**
        * 每页显示条数
        */
        @ApiModelProperty(value = "每页显示条数")
        private Integer size;
        /**
        * 总条数
        */
        @ApiModelProperty(value = "总条数")
        private Integer total;
        /**
        * 总页数
        */
        @ApiModelProperty(value = "总页数")
        private Integer totalPage;
        /**
        * 数据
        */
        @ApiModelProperty(value = "数据与信息")
        private T data;
}
