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
 * 用户与角色中间表(UserToRole)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户与角色关系信息", description = "用户与角色关系信息表")
@ApiSupport(author = "JanYork")
public class UserToRole extends Model<UserToRole> {
    private static final long serialVersionUID = 5690454339234842044L;
    /**
     * 用户与角色中间表ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户与角色中间表ID")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
}
