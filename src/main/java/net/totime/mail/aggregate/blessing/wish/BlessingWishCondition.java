/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.aggregate.blessing.wish;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/08
 * @description 愿望祝福基本查询条件
 * @since 1.0.0
 */
@ApiModel("愿望祝福基本查询条件")
@ApiSupport(author = "JanYork")
@Data
public class BlessingWishCondition {
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 愿望ID
     */
    @ApiModelProperty(value = "愿望ID")
    private Long wishId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 是否过滤
     */
    @ApiModelProperty(value = "是否过滤")
    private Boolean isFilter;
}