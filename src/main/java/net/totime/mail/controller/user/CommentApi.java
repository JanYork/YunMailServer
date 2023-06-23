/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.dto.CommentDTO;
import net.totime.mail.entity.Comment;
import net.totime.mail.entity.User;
import net.totime.mail.handler.BaiDuAiHandler;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.CommentService;
import net.totime.mail.service.LetterService;
import net.totime.mail.service.MailService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.CheckReturn;
import net.totime.mail.util.RedisUtil;
import net.totime.mail.vo.CommentVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 邮件与信件评论信息
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-06-14 22:59:34
 */
@RestController
@RequestMapping("/user/comment")
@Api(tags = "[用户]评论相关接口")
@Slf4j
@Validated
public class CommentApi {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private LetterService letterService;
    @Resource
    private MailService mailService;
    @Resource
    private BaiDuAiHandler aiHandler;
    @Resource
    private MapperFacade mapperFacade;
    @Resource
    private RedisUtil rut;

    /**
     * 评论
     *
     * @param commentDTO 评论信息
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/insert")
    @ApiOperation(value = "用户评论")
    public ApiResponse<Boolean> commentByMail(@RequestBody @Valid CommentDTO commentDTO) {
        CheckReturn<Comment> check = check(commentDTO);
        if (!check.getStatus()) {
            return ApiResponse.fail(false).message(check.getMsg());
        }
        Comment comment = check.getValue();
        comment.setUserId(StpUtil.getLoginIdAsLong());
        comment.setCreateTime(new Date());
        comment.setIsFilter(false);
        comment.setIsSensitive(false);
        boolean save = commentService.save(comment);
        aiHandler.commentAiCheck(comment);
        return save ? ApiResponse.ok(true).message("评论成功") : ApiResponse.fail(false).message("系统异常");
    }

    /**
     * 软删除评论
     *
     * @param id 评论id
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除评论")
    public ApiResponse<Boolean> deleteComment(@RequestParam @Valid @NotNull(message = "评论ID不能为空") String id) {
        Comment comment = commentService.getById(Long.parseLong(id));
        if (comment == null) {
            return ApiResponse.fail(false).message("评论不存在");
        }
        if (!comment.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            log.warn("用户{}无权限删除评论{}", StpUtil.getLoginIdAsLong(), id);
            return ApiResponse.fail(false).message("无权限删除！");
        }
        comment.setIsFilter(true);
        boolean remove = commentService.updateById(comment);
        return remove ? ApiResponse.ok(true).message("删除成功") : ApiResponse.fail(false).message("系统异常");
    }

    /**
     * 获取评论
     *
     * @param id   对应邮件或信件id
     * @param type 0：邮件 1：信件
     * @return {@link ApiResponse}<{@link Comment}>
     */
    @GetMapping("/list/{page}/{size}")
    @ApiOperation(value = "获取评论")
    public ApiResponse<List<CommentVO>> getComment(@RequestParam @Valid @NotNull(message = "ID不能为空") String id,
                                                   @RequestParam @Valid @NotNull(message = "类型不能为空") Integer type,
                                                   @PathVariable @Valid @NotNull(message = "页码不能为空") Integer page,
                                                   @PathVariable @Valid @NotNull(message = "页数不能为空") Integer size
    ) {
        List<Comment> comments = commentService.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<>(Comment.class)
                        .eq(Comment::getMailOrLetterId, id)
                        .eq(Comment::getForType, type)
                        .eq(Comment::getIsFilter, false)
                        .orderByDesc(Comment::getCreateTime)
        ).getRecords();
        if (comments.isEmpty()) {
            return ApiResponse.<List<CommentVO>>ok(null).message("暂无评论");
        }
        List<CommentVO> tree = toTree(mapperFacade.mapAsList(comments, CommentVO.class), 0L);
        return ApiResponse.ok(tree).message("获取成功");
    }

    /**
     * 校验
     *
     * @param commentDTO 评论信息
     */
    private CheckReturn<Comment> check(CommentDTO commentDTO) {
        int level = 1;
        if (commentDTO.getParentId() != null && commentDTO.getParentId() != 0) {
            Comment comment = commentService.getById(commentDTO.getParentId());
            if (comment == null) {
                return CheckReturn.fail("父级评论不存在");
            }
            level = comment.getLevel() + 1;
            if (level > 3) {
                return CheckReturn.fail("非法层级");
            }
        }
        if (commentDTO.getForType() == 0) {
            if (mailService.getById(commentDTO.getMailOrLetterId()) == null) {
                return CheckReturn.fail("邮件不存在");
            }
        } else {
            if (letterService.getById(commentDTO.getMailOrLetterId()) == null) {
                return CheckReturn.fail("信件不存在");
            }
        }
        // 10S内容防重
        int hash = commentDTO.getContent().hashCode();
        Long incr = rut.incr("comment:" + hash, 10L);
        if (incr > 1) {
            return CheckReturn.fail("请勿重复内容提交");
        }
        Comment r = mapperFacade.map(commentDTO, Comment.class);
        r.setLevel(level);
        return CheckReturn.ok(r);
    }

    /**
     * 转树
     *
     * @param comments 评论
     * @param parentId 父级ID
     * @return {@link List}<{@link CommentVO}>
     */
    private List<CommentVO> toTree(List<CommentVO> comments, Long parentId) {
        return comments.stream()
                .filter(comment -> comment.getParentId().equals(parentId))
                .peek(comment -> {
                    // 查询用户信息
                    User user = userService.getById(comment.getUserId());
                    if (user != null) {
                        comment.setUser(mapperFacade.map(user, CommentVO.UserVO.class));
                    }
                    comment.setChildren(toTree(comments, comment.getId()));
                })
                .collect(Collectors.toList());
    }
}
