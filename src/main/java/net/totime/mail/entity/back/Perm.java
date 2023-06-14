/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限表(Perm)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "权限信息", description = "权限信息表")
@ApiSupport(author = "JanYork")
public class Perm extends Model<Perm> {
    private static final long serialVersionUID = -6207978072792902616L;
    /**
     * 权限ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "权限ID")
    private Integer id;
    /**
     * 权限路径
     */
    @ApiModelProperty(value = "权限路径")
    private String permissions;
    /**
     * 权限描述
     */
    @ApiModelProperty(value = "权限描述")
    private String desc;
}
