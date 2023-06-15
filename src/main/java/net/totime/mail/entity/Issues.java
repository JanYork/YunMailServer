/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 考验问题信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户考验问题表", description = "字段与数据库一致")
public class Issues extends Model<Issues> {
    private static final long serialVersionUID = 4487129339117857476L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;
    /**
     * 问题
     */
    @ApiModelProperty(value = "问题", example = "1", required = true)
    @NotNull(message = "问题不能为空")
    private String issue;
}
