package cn.totime.data.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (WorkTicketsInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkTicketsInfo extends Model<WorkTicketsInfo> {
    private static final long serialVersionUID = -777155964381860639L;

    /**
     * 工单自增ID
     */
    private Integer workTicketsId;
    /**
     * 工单标题
     */
    private String workTicketsTitle;
    /**
     * 工单内容
     */
    private String workTicketsContent;
    /**
     * 工单类型
     */
    private Integer workTicketsType;
    /**
     * 提交用户ID
     */
    private Long uId;
    /**
     * 工单状态
     */
    private Integer workTicketsState;
}