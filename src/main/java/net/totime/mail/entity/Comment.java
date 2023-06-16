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
 * 邮件与信件评论信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "邮件与信件评论信息", description = "字段与数据库一致")
public class Comment extends Model<Comment> {
    private static final long serialVersionUID = 3949089601291348988L;
    /**
     * 评论ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "评论ID", example = "1")
    private Long id;
    /**
     * 父级评论ID
     */
    @ApiModelProperty(value = "父级评论ID", example = "0")
    private Long parentId;
    /**
     * 评论用户ID
     */
    @ApiModelProperty(value = "评论用户ID", example = "1432543643654754", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 评论对应邮件或者信件
     */
    @ApiModelProperty(value = "评论对应邮件或者信件", example = "10001", required = true)
    @NotNull(message = "评论对象不能为空")
    private Long mailOrLetterId;
    /**
     * 评论对应表[mail：0，letter：1]
     */
    @ApiModelProperty(value = "评论对应表[mail：0，letter：1]", example = "0", required = true)
    @NotNull(message = "评论类型不能为空")
    private Integer forType;
    /**
     * 评论深度
     */
    @ApiModelProperty(value = "评论深度", example = "1")
    private Integer level;
    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", example = "你好", required = true)
    @NotNull(message = "评论内容不能为空")
    @Size(min = 1, max = 255, message = "内容长度非法")
    private String content;
    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间", example = "2023-06-14 22:59:34")
    private Date createTime;
    /**
     * 是否过滤
     */
    @ApiModelProperty(value = "是否过滤", example = "0", required = true)
    @NotNull(message = "是否过滤必须")
    private Boolean isFilter;
}
