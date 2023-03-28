package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限表(Perm)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Perm extends Model<Perm> {
    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限路径
     */
    private String permissions;
    /**
     * 权限描述
     */
    private String desc;
}