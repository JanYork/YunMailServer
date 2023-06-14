/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信任务表(Message)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "短信信息", description = "短信信息表")
@ApiSupport(author = "JanYork")
public class Message extends Model<Message> {
    private static final long serialVersionUID = -1877621864918384177L;
    /**
     * 短信ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "短信ID")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 短信内容
     */
    @ApiModelProperty(value = "短信内容")
    private String text;
    /**
     * 短信发送时间
     */
    @ApiModelProperty(value = "短信发送时间")
    private Date sendTime;
    /**
     * 短信创建时间
     */
    @ApiModelProperty(value = "短信创建时间")
    private Date createTime;
    /**
     * 短信是否匿名
     */
    @ApiModelProperty(value = "短信是否匿名")
    private Integer isUnnamed;
    /**
     * 短信状态
     */
    @ApiModelProperty(value = "短信状态")
    private Integer state;
}
