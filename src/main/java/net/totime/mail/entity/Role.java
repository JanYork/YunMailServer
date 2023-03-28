package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表(Role)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends Model<Role> {
    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
}