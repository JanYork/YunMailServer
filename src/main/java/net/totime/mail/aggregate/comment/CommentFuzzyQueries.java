/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.aggregate.comment;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/08
 * @description 评论模糊查询条件
 * @since 1.0.0
 */
@ApiModel("评论模糊查询")
@ApiSupport(author = "JanYork")
@Data
public class CommentFuzzyQueries {
    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", example = "评论.......")
    private String content;
}
