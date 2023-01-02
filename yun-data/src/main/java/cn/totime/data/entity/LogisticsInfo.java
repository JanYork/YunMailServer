package cn.totime.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (LogisticsInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:43:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogisticsInfo extends Model<LogisticsInfo> {
    private static final long serialVersionUID = 4509107004485317889L;

    /**
     * 物流订单ID
     */
    @TableId
    private Long logisticsId;
    /**
     * 物流类型
     */
    private Integer logisticsType;
    /**
     * 信件ID
     */
    private Integer letterId;
    /**
     * 物流状态
     */
    private Integer logisticsState;
}