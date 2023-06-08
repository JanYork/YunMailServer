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
 * 愿望祝福表(Blessing)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "愿望祝福语", description = "愿望祝福语")
@ApiSupport(author = "JanYork")
public class Blessing extends Model<Blessing> {
    private static final long serialVersionUID = -7284153583904493598L;
    /**
     * 祝福ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "祝福ID", example = "1")
    private Integer id;
    /**
     * 祝福分类ID
     */
    @ApiModelProperty(value = "祝福分类ID", example = "1", required = true)
    private Integer categoryId;
    /**
     * 祝福内容
     */
    @ApiModelProperty(value = "祝福内容", example = "祝愿你的愿望早日实现", required = true)
    private String content;
}
