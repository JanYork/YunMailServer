package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 祝福语录表(BlessingToWish)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlessingToWish extends Model<BlessingToWish> {
    /**
     * 祝福语ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 祝福语
     */
    private Integer blessing;
    /**
     * 愿望ID
     */
    private Long wishId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否过滤
     */
    private Integer isFilter;
}