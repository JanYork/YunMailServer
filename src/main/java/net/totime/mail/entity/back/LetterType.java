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
 * 信件类型表(LetterType)表实体类
 *
 * @author JanYork
 * @since 2023-03-29 10:46:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "信件类型信息", description = "信件类型信息表")
@ApiSupport(author = "JanYork")
public class LetterType extends Model<LetterType> {
    private static final long serialVersionUID = 4773048251137248174L;
    /**
     * 信件类型ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "信件类型ID")
    private Integer id;
    /**
     * 信件类型名称
     */
    @ApiModelProperty(value = "信件类型名称", required = true)
    private String name;
}
