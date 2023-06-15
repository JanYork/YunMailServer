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
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 短信任务信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "短信任务信息", description = "字段与数据库一致")
public class Message extends Model<Message> {
    private static final long serialVersionUID = 537104535127795029L;
    /**
     * 短信ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "短信ID", example = "1")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 短信内容
     */
    @ApiModelProperty(value = "短信内容", example = "1", required = true)
    @NotNull(message = "内容不能为空")
    @Size(min = 1, max = 300, message = "内容长度在1-300之间")
    private String text;
    /**
     * 短信发送时间
     */
    @ApiModelProperty(value = "短信发送时间", example = "1", required = true)
    @NotNull(message = "发送时间不能为空")
    private Date sendTime;
    /**
     * 短信创建时间
     */
    @ApiModelProperty(value = "短信创建时间", example = "1")
    private Date createTime;
    /**
     * 短信是否匿名
     */
    @ApiModelProperty(value = "短信是否匿名", example = "true", required = true)
    @NotNull(message = "是否匿名?")
    private Boolean isUnnamed;
    /**
     * 短信状态
     */
    @ApiModelProperty(value = "短信状态", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Integer state;
}
