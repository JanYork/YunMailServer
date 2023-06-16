/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.totime.mail.entity.Comment;
import net.totime.mail.service.CommentService;
import net.totime.mail.vo.CommentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/23
 * @description 评论操作服务
 * @since 1.0.0
 */
@Service
public class CommentOperate {
    @Resource
    private CommentService commentService;

    private static final Integer MAX_LEVEL = 3;

    /**
     * 查询评论深度
     *
     * @param commentId 评论ID
     * @return 评论深度
     */
    public Integer getCommentLevel(Long commentId) {
        Comment comment = commentService.getById(commentId);
        return comment.getLevel();
    }

    /**
     * 根据信件/邮件ID查询评论
     *
     * @param mailOrLetterId 信件/邮件ID
     * @param page           页码
     * @param size           每页数量
     * @param type           评论类型
     * @return 评论
     */
    public List<CommentVO> getCommentByMailOrLetterId(String mailOrLetterId, Integer page, Integer size, Integer type) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getMailOrLetterId, mailOrLetterId);
        wrapper.eq(Comment::getForType, type);
        wrapper.eq(Comment::getIsFilter, false);
        wrapper.orderByDesc(Comment::getCreateTime);
        wrapper.eq(Comment::getParentId, 0);
        List<Comment> comments = commentService.page(
                new Page<>(page, size),
                wrapper
        ).getRecords();
        return convertTree(comments);
    }

    /**
     * 根据父亲ID查询子评论
     *
     * @param parentId 父亲ID
     * @param page     页码
     * @param size     每页数量
     * @return 子评论
     */
    public List<Comment> getCommentByParentId(Long parentId, Integer page, Integer size) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, parentId);
        wrapper.eq(Comment::getIsFilter, false);
        wrapper.orderByDesc(Comment::getCreateTime);
        return commentService.page(
                new Page<>(page, size),
                wrapper
        ).getRecords();
    }

    /**
     * 递归转换评论树
     *
     * @param comments 父级评论集合
     * @return {@link List}<{@link CommentVO}> 评论树
     */
    private List<CommentVO> convertTree(List<Comment> comments) {
        return comments.stream().map(this::toVo).peek(
                subset -> {
                    List<Comment> collect = getCommentByParentId(subset.getId(), 1, 10);
                    if (collect.size() > 0) {
                        subset.setChildren(convertTree(collect));
                        convertTree(collect);
                    }
                }
        ).collect(Collectors.toList());
    }

    /**
     * 评论转换为VO
     *
     * @param c 评论
     * @return {@link CommentVO}
     */
    private CommentVO toVo(Comment c) {
        return CommentVO.builder()
                .id(c.getId())
                .parentId(c.getParentId())
                .userId(c.getUserId())
                .mailOrLetterId(c.getMailOrLetterId())
                .content(c.getContent())
                .createTime(c.getCreateTime())
                .isFilter(c.getIsFilter())
                .build();
    }
}
