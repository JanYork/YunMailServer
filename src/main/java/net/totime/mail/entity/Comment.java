package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 邮件与信件评论表(Comment)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Comment extends Model<Comment> {
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
    private String mailOrLetterId;
    /**
     * 评论对应表[mail：0，letter：1]     */
    private Integer forType;
    /**
     * 评论深度
     */
    private Integer level;
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
    private Integer isFilter;
}