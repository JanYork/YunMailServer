/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 状态类型表(StateType)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "状态类型信息", description = "状态类型信息表")
@ApiSupport(author = "JanYork")
public class StateType extends Model<StateType> {
    private static final long serialVersionUID = -1144087302618358554L;
    /**
     * 状态类型ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "状态类型ID")
    private Integer id;
    /**
     * 状态名称
     */
    @ApiModelProperty(value = "状态名称")
    private String state;
}
