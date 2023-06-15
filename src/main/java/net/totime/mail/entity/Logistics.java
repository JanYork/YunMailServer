/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 信件物流信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "信件物流信息", description = "字段与数据库一致")
public class Logistics extends Model<Logistics> {
    private static final long serialVersionUID = -7466009741594406743L;
    /**
     * 物流ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "物流ID", example = "1")
    private Long id;
    /**
     * 信件ID
     */
    @ApiModelProperty(value = "信件ID", example = "1", required = true)
    @NotNull(message = "信件不能为空")
    private Long letterId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2023-06-14 22:59:37")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2023-06-14 22:59:37")
    private Date updateTime;
    /**
     * 物流单号
     */
    @ApiModelProperty(value = "物流单号", example = "SF123456789", required = true)
    @NotNull(message = "单号不能为空")
    private String logisticsId;
    /**
     * 物流商户['SF','YZ','YT'...]
     */
    @ApiModelProperty(value = "物流商户['SF','YZ','YT'...]", example = "SF", required = true)
    @NotNull(message = "商户不能为空")
    private String logisticsCode;
    /**
     * 物流状态
     */
    @ApiModelProperty(value = "物流状态", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;
}
