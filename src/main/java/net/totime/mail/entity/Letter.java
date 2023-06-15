/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 信件信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "信件信息", description = "字段与数据库一致")
public class Letter extends Model<Letter> {
    private static final long serialVersionUID = 8684701593393972745L;
    /**
     * 信件唯一ID
     */
    @TableId
    @ApiModelProperty(value = "信件唯一ID", example = "1", required = true)
    private Long letterId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    /**
     * 信件标题
     */
    @ApiModelProperty(value = "信件标题", example = "1", required = true)
    @NotNull(message = "信件标题不能为空")
    @Size(min = 1, max = 30, message = "信件标题长度必须在1-30之间")
    private String letterTitle;
    /**
     * 信件内容
     */
    @ApiModelProperty(value = "信件内容", example = "1", required = true)
    @NotNull(message = "信件内容不能为空")
    @Size(min = 1, max = 5000, message = "信件内容长度必须在1-5000之间")
    private String letterContent;
    /**
     * 信件创建时间
     */
    @ApiModelProperty(value = "信件创建时间", example = "1")
    private Date letterCreateTime;
    /**
     * 信件是否公开
     */
    @ApiModelProperty(value = "信件是否公开", example = "1", required = true)
    @NotNull(message = "是否公开?")
    private Boolean isPublic;
    /**
     * 信件发送时间
     */
    @ApiModelProperty(value = "信件发送时间", example = "1", required = true)
    @NotNull(message = "发信时间不能为空")
    private Date goToTime;
    /**
     * 信件是否发给自己
     */
    @ApiModelProperty(value = "是否发给自己", example = "1", required = true)
    @NotNull(message = "是否发给自己?")
    private Boolean isYourself;
    /**
     * 信件发往地址 TODO：优化为省市区经纬度
     */
    @ApiModelProperty(value = "信件发往地址", example = "1", required = true)
    @NotNull(message = "地址不能为空")
    private String address;
    /**
     * 信件收件人姓名
     */
    @ApiModelProperty(value = "信件收件人姓名", example = "1", required = true)
    @NotNull(message = "收件人不能为空")
    private String addressee;
    /**
     * 信件收信人手机号
     */
    @ApiModelProperty(value = "信件收信人手机号", example = "1", required = true)
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    /**
     * 信件备注
     */
    @ApiModelProperty(value = "信件备注", example = "1")
    private String remark;
    /**
     * 信件类型
     */
    @ApiModelProperty(value = "信件类型", example = "1", required = true)
    @NotNull(message = "类型不能为空")
    private Integer letterType;
    /**
     * 信件状态
     */
    @ApiModelProperty(value = "信件状态", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Integer state;
}
