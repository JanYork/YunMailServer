package cn.totime.data.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (WishInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WishInfo extends Model<WishInfo> {
    private static final long serialVersionUID = -5124034006098424986L;

    /**
     * 许愿表自增ID
     */
    private Integer wishId;
    /**
     * 用户ID
     */
    private Long uId;
    /**
     * 许愿内容
     */
    private String wishText;
    /**
     * 许愿点赞数
     */
    private Integer wishTsan;
    /**
     * 许愿状态
     */
    private Integer wishState;
}