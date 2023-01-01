package cn.totime.data.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (WorkTicketsType)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkTicketsType extends Model<WorkTicketsType> {
    private static final long serialVersionUID = 3635879366547211984L;

    /**
     * 工单类型ID
     */
    private Integer workTicketsTypeId;
    /**
     * 工单类型名称
     */
    private String workTicketsTypeName;
}