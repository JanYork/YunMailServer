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
 * 权限信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "权限信息")
public class Perm extends Model<Perm> {
    private static final long serialVersionUID = -6207978072792902616L;
    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "权限ID", example = "1")
    private Integer id;
    /**
     * 权限路径
     */
    @ApiModelProperty(value = "权限路径", example = "/user", required = true)
    @NotNull(message = "权限路径不能为空")
    private String permissions;
    /**
     * 权限描述
     */
    @ApiModelProperty(value = "权限描述", example = "用户管理", required = true)
    @NotNull(message = "权限描述不能为空")
    private String desc;
}
