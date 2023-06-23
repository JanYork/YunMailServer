/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 邮件与信件评论信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:35
 */
@Data
@ApiModel(value = "邮件与信件评论信息", description = "用户端数据")
public class CommentVO {
    /**
     * 评论ID
     */
    @ApiModelProperty(value = "评论ID", example = "1")
    private Long id;
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
     * 评论用户ID
     */
    @ApiModelProperty(value = "评论用户ID", example = "1432543643654754", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", example = "你好", required = true)
    @NotNull(message = "评论内容不能为空")
    @Size(min = 1, max = 255, message = "内容长度非法")
    private String content;
    /**
     * 评论用户信息
     */
    @ApiModelProperty(value = "评论用户信息")
    private UserVO user;
    /**
     * 子评论信息
     */
    @ApiModelProperty(value = "子评论信息")
    private List<CommentVO> children;

    @Data
    @ApiModel(value = "用户信息", description = "用户端数据")
    public static class UserVO {
        /**
         * 用户ID
         */
        @ApiModelProperty(value = "用户ID", example = "1")
        private Long id;
        /**
         * 用户昵称
         */
        @ApiModelProperty(value = "用户昵称", example = "JanYork")
        private String nickName;
        /**
         * 用户头像
         */
        @ApiModelProperty(value = "用户头像", example = "https://www.baidu.com/img/bd_logo1.png")
        private String avatar;
    }
}
