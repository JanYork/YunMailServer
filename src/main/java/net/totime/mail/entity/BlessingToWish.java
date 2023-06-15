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
 * 心愿祝福信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "心愿祝福信息", description = "字段与数据库一致")
public class BlessingToWish extends Model<BlessingToWish> {
    private static final long serialVersionUID = 5031769277033174217L;
    /**
     * 祝福ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "祝福ID", example = "1")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1432543643654754", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 祝福语ID
     */
    @ApiModelProperty(value = "祝福语ID", example = "1", required = true)
    @NotNull(message = "祝福语不能为空")
    private Integer blessing;
    /**
     * 心愿ID
     */
    @ApiModelProperty(value = "心愿ID", example = "1324357465786897", required = true)
    @NotNull(message = "心愿不能为空")
    private Long wishId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2023-06-14 22:59:34")
    private Date createTime;
    /**
     * 是否过滤
     */
    @ApiModelProperty(value = "是否过滤", example = "0", required = true, notes = "详细数据参考枚举类")
    @NotNull(message = "是否过滤不能为空")
    private Integer isFilter;
}
