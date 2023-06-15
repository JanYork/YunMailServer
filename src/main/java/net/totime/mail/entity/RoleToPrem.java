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
 * 角色与权限关联信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "角色与权限关联信息")
public class RoleToPrem extends Model<RoleToPrem> {
    private static final long serialVersionUID = 3727365130422113901L;
    /**
     * 角色与权限中间表ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID", example = "1")
    private Integer id;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", example = "1", required = true)
    @NotNull(message = "角色不能为空")
    private Integer roleId;
    /**
     * 权限ID
     */
    @ApiModelProperty(value = "权限ID", example = "1", required = true)
    @NotNull(message = "权限不能为空")
    private Integer premId;
}
