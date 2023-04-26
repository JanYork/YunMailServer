package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private static final long serialVersionUID = -4442326084905064881L;
    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
}
