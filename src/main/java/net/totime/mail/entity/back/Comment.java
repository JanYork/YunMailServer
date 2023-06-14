/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 邮件与信件评论表(Comment)表实体类
 *
 * @author JanYork
 * @since 2023-04-23 14:54:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "评论信息", description = "邮件与信件评论表")
@ApiSupport(author = "JanYork")
public class Comment extends Model<Comment> {
    private static final long serialVersionUID = 3949089601291348988L;
    /**
     * 评论ID TODO: 评论ID改为自增
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "评论ID", example = "1")
    private Long id;
    /**
     * 父级评论ID
     */
    @ApiModelProperty(value = "父级评论ID", example = "1", required = true)
    private Long parentId;
    /**
     * 评论用户ID
     */
    @ApiModelProperty(value = "评论用户ID", example = "202388888888", required = true)
    private Long userId;
    /**
     * 评论对应邮件或者信件
     */
    @ApiModelProperty(value = "评论对应邮件或者信件", example = "1", required = true)
    private String mailOrLetterId;
    /**
     * 评论对应表[mail：0，letter：1]
     */
    @ApiModelProperty(value = "评论对应表[mail：0，letter：1]", example = "0", required = true)
    private Integer forType;
    /**
     * 评论深度
     */
    @ApiModelProperty(value = "评论深度", example = "1", required = true)
    private Integer level;
    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", example = "评论.......", required = true)
    private String content;
    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间", required = true)
    private Date createTime;
    /**
     * 是否过滤
     */
    @ApiModelProperty(value = "是否过滤", example = "false", required = true)
    private Boolean isFilter;
}
