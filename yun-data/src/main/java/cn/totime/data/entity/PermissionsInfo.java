package cn.totime.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (PermissionsInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionsInfo extends Model<PermissionsInfo> {
    private static final long serialVersionUID = 1831332155389112955L;

    /**
     * 权限表自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer permissionsId;
    /**
     * 资源权限名称
     */
    private String permissionsName;
    /**
     * 资源地址路径
     */
    private String permissionsUrl;
}