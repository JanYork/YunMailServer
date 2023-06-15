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
 * 信件类型信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "信件类型信息", description = "字段与数据库表字段一致")
public class LetterType extends Model<LetterType> {
    private static final long serialVersionUID = 4773048251137248174L;
    /**
     * 信件类型ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "信件类型ID", example = "1")
    private Integer id;
    /**
     * 信件类型名称
     */
    @ApiModelProperty(value = "信件类型名称", example = "1", required = true)
    @NotNull(message = "类型名称不能为空")
    private String name;
}
