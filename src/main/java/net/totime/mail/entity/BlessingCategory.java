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
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 祝福分类表(BlessingCategory)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
@ApiSupport(author = "JanYork")
public class BlessingCategory extends Model<BlessingCategory> {
    private static final long serialVersionUID = -634102198329007635L;
    /**
     * 祝福分类ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "祝福分类ID", example = "1")
    private Integer id;
    /**
     * 祝福分类名称
     */
    @ApiModelProperty(value = "祝福分类名称", example = "生日祝福", required = true)
    private String name;
}
