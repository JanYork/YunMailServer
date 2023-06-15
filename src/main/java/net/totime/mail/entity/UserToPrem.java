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
 * 用户与权限关联信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户与权限关系信息", description = "字段与数据库一致")
public class UserToPrem extends Model<UserToPrem> {
    private static final long serialVersionUID = -4307506805024223530L;
    /**
     * 用户与权限中间表ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID", example = "1")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 权限ID
     */
    @ApiModelProperty(value = "权限ID", example = "1", required = true)
    @NotNull(message = "权限不能为空")
    private Integer premId;
}
