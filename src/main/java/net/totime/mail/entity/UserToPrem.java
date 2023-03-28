package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户与权限中间表(UserToPrem)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserToPrem extends Model<UserToPrem> {
    /**
     * 用户与权限中间表ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 权限ID
     */
    private Integer premId;
}