package cn.totime.data.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (RoleInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleInfo extends Model<RoleInfo> {
    private static final long serialVersionUID = -7998857633744869373L;

    /**
     * 角色表自增ID
     */
    private Integer roleId;
    /**
     * 用户角色名称
     */
    private String roleName;
}