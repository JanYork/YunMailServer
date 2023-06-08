/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.aggregate.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @description 管理员基本查询条件
 * @since 1.0.0
 */
@ApiModel("管理员基本查询条件")
@ApiSupport(author = "JanYork")
@Data
public class AdminCondition {
    /**
     * 管理员角色
     */
    @TableField("role")
    @ApiModelProperty(value = "管理员角色", example = "admin")
    private String userRole;
    /**
     * 管理员创建时间
     */
    @ApiModelProperty(value = "管理员创建时间")
    private Date createTime;
}
