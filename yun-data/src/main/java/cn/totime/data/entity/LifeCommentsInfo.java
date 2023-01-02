package cn.totime.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (LifeCommentsInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:41:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LifeCommentsInfo extends Model<LifeCommentsInfo> {
    private static final long serialVersionUID = -2131097451916087219L;

    /**
     * 微社区评论自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer lifeCommentsId;
    /**
     * 评论用户ID
     */
    private Long commentsUid;
    /**
     * 评论内容
     */
    private String commentsContent;
    /**
     * 社区发表内容ID
     */
    private Integer lifeId;
    /**
     * 评论父级ID
     */
    private Integer commentsParentId;
}