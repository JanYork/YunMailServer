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

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/08
 * @description 评论直接查询条件
 * @since 1.0.0
 */
@ApiModel("评论直接查询条件")
@ApiSupport(author = "JanYork")
@Data
public class CommentQueries {
    /**
     * 评论ID TODO: 评论ID改为自增
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "评论ID", example = "1")
    private Long id;
}
