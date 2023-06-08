/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 祝福(BlessingToWish)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "祝福语信息", description = "祝福语")
public class BlessingToWish extends Model<BlessingToWish> {
    private static final long serialVersionUID = 5031769277033174217L;
    /**
     * 祝福语ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "祝福语ID", example = "1")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "202388888888", required = true)
    private Long userId;
    /**
     * 祝福语
     */
    @ApiModelProperty(value = "祝福语ID", example = "1", required = true)
    private Integer blessing;
    /**
     * 愿望ID TODO: 改为自增列
     */
    @ApiModelProperty(value = "愿望ID", example = "1", required = true)
    private Long wishId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    /**
     * 是否过滤
     */
    @ApiModelProperty(value = "是否过滤", example = "true", required = true)
    private Boolean isFilter;
}
