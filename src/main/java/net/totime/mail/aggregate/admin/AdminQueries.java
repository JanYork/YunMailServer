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
 * @description 管理员直接查询条件
 * @since 1.0.0
 */
@ApiModel("管理员直接查询条件")
@ApiSupport(author = "JanYork")
@Data
public class AdminQueries {
    @ApiModelProperty(value = "管理员ID", example = "1")
    private Integer id;
    /**
     * 管理员用户
     */
    @ApiModelProperty(value = "管理员用户", example = "admin")
    private String name;
    /**
     * 管理员邮箱
     */
    @ApiModelProperty(value = "管理员邮箱", example = "123456@qq.com")
    private String mail;
}
