/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 邮件与信件评论表(Comment)表实体类
 *
 * @author JanYork
 * @since 2023-04-23 14:54:45
 */
@Data
@Builder
public class CommentVO {
    /**
     * 评论ID
     */
    private Long id;
    /**
     * 父级评论ID
     */
    private Long parentId;
    /**
     * 评论用户ID
     */
    private Long userId;
    /**
     * 评论对应邮件或者信件
     */
    private Long mailOrLetterId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    private Date createTime;
    /**
     * 是否过滤
     */
    private Boolean isFilter;
    /**
     * 子评论
     */
    private List<CommentVO> children;
}
