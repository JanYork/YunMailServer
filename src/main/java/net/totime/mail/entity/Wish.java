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
import java.util.Date;

/**
 * 心愿信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "心愿信息", description = "字段与数据库一致")
public class Wish extends Model<Wish> {
    private static final long serialVersionUID = -6659428340822708387L;
    /**
     * 愿望ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "愿望ID", example = "1")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 愿望
     */
    @ApiModelProperty(value = "愿望", example = "1", required = true)
    @NotNull(message = "愿望不能为空")
    @Size(min = 10, max = 100, message = "字数必须在1-100之间")
    private String text;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片", example = "1")
    private String image;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "1")
    private Date creatTime;
    /**
     * 心愿状态
     */
    @ApiModelProperty(value = "心愿状态", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Integer state;
}
