package cn.totime.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (RealNameInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RealNameInfo extends Model<RealNameInfo> {
    private static final long serialVersionUID = -7292586562050558594L;

    /**
     * 实名信息表自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer realId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String realIdcard;
}

