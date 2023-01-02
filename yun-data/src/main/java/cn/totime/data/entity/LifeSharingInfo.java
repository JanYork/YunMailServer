package cn.totime.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (LifeSharingInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:42:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LifeSharingInfo extends Model<LifeSharingInfo> {
    private static final long serialVersionUID = 1608539061019207800L;

    /**
     * 微社区自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer lifeSharingId;
    /**
     * 用户ID
     */
    private Long uId;
    /**
     * 社区发表标题
     */
    private String lifeSharingTitle;
    /**
     * 社区发表内容
     */
    private String lifeSharingContent;
    /**
     * 社区发表内容热度
     */
    private Integer lifeSharingHeat;
    /**
     * 社区发表内容点赞数
     */
    private Integer lifeSharingTsan;
    /**
     * 社区发表内容海报相对URL
     */
    private String lifeSharingPosters;
    /**
     * 社区发表内容点击数
     */
    private Integer lifeSharingClick;
}