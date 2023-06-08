/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.aggregate.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/08
 * @description 评论基本查询条件
 * @since 1.0.0
 */
@ApiModel("评论基本查询条件")
@ApiSupport(author = "JanYork")
@Data
public class CommentCondition {
    /**
     * 父级评论ID
     */
    @ApiModelProperty(value = "父级评论ID", example = "1")
    private Long parentId;
    /**
     * 评论用户ID
     */
    @ApiModelProperty(value = "评论用户ID", example = "202388888888")
    private Long userId;
    /**
     * 评论对应邮件或者信件
     */
    @ApiModelProperty(value = "评论对应邮件或者信件", example = "1")
    private String mailOrLetterId;
    /**
     * 评论对应表[mail：0，letter：1]
     */
    @ApiModelProperty(value = "评论对应表[mail：0，letter：1]", example = "0")
    private Integer forType;
    /**
     * 评论深度
     */
    @ApiModelProperty(value = "评论深度", example = "1")
    private Integer level;
    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    private Date createTime;
    /**
     * 是否过滤
     */
    @ApiModelProperty(value = "是否过滤", example = "false")
    private Boolean isFilter;
}
