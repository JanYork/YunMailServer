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
import javax.validation.constraints.Size;

/**
 * 祝福语信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "祝福语信息", description = "字段与数据库一致")
public class Blessing extends Model<Blessing> {
    private static final long serialVersionUID = -7284153583904493598L;
    /**
     * 祝福ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "祝福语ID", example = "1")
    private Integer id;
    /**
     * 祝福分类ID
     */
    @ApiModelProperty(value = "祝福语分类ID", example = "1", required = true)
    @NotNull(message = "分类不能为空")
    private Integer categoryId;
    /**
     * 祝福内容
     */
    @ApiModelProperty(value = "祝福语内容", example = "祝云寄越来越好", required = true)
    @NotNull(message = "内容不能为空")
    @Size(min = 10, max = 255, message = "内容长度在10-255之间")
    private String content;
}
