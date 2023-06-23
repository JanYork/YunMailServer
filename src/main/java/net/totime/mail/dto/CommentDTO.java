/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 邮件与信件评论
 *
 * @author JanYork
 * @since 2023-06-14 22:59:35
 */
@Data
@ApiModel(value = "邮件与信件评论", description = "用户ID会根据token自动填充，必须登录，字段字数均有限制")
public class CommentDTO {
    /**
     * 父级评论ID
     */
    @ApiModelProperty(value = "父级评论ID", example = "0")
    private Long parentId;
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
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", example = "你好", required = true)
    @NotNull(message = "评论内容不能为空")
    @Size(min = 1, max = 255, message = "内容长度非法")
    private String content;
}
