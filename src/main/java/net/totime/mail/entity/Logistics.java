package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 信件物流表(Logistics)表实体类
 *
 * @author JanYork
 * @since 2023-05-12 15:27:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Logistics extends Model<Logistics> {
    private static final long serialVersionUID = 2112410038577839855L;
    /**
     * 物流ID
     */
    private Integer id;
    /**
     * 信件ID
     */
    private String letterId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 物流单号
     */
    private String logisticsId;
    /**
     * 物流商户['SF','YZ','YT'...]
     */
    private String logisticsCode;
    /**
     * 物流状态
     */
    private Integer status;
}
