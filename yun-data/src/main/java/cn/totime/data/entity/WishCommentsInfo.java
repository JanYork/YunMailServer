package cn.totime.data.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (WishCommentsInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WishCommentsInfo extends Model<WishCommentsInfo> {
    private static final long serialVersionUID = -2872601758355161968L;

    /**
     * 愿望评论表自增ID
     */
    private Integer wishCommentsId;
    /**
     * 评论用户ID
     */
    private Long commentsUid;
    /**
     * 评论内容
     */
    private String commentsContent;
    /**
     * 愿望ID
     */
    private Integer wishId;
    /**
     * 评论父级ID
     */
    private Integer commentsParentId;
}