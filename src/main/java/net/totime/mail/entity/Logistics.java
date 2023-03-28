package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 信件物流表(Logistics)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Logistics extends Model<Logistics> {
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
     * 物流状态
     */
    private Integer status;
}