/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.entity.back;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.totime.mail.enums.LetterState;

/**
 * 时光信件表(Letter)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "信件信息", description = "信件信息表")
@ApiSupport(author = "JanYork")
public class Letter extends Model<Letter> {
    private static final long serialVersionUID = 8349360044676853889L;
    /**
     * 信件唯一ID
     */
    @TableId
    @ApiModelProperty(value = "信件唯一ID")
    private String letterId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 信件标题
     */
    @ApiModelProperty(value = "信件标题")
    private String letterTitle;
    /**
     * 信件内容
     */
    @ApiModelProperty(value = "信件内容")
    private String letterContent;
    /**
     * 信件创建时间
     */
    @ApiModelProperty(value = "信件创建时间")
    private Date letterCreateTime;
    /**
     * 信件是否公开
     */
    @ApiModelProperty(value = "信件是否公开")
    private Boolean isPublic;
    /**
     * 信件发送时间
     */
    @ApiModelProperty(value = "信件发送时间")
    private Date goToTime;
    /**
     * 信件是否发给自己
     */
    @ApiModelProperty(value = "信件是否发给自己")
    private Boolean isYourself;
    /**
     * 信件发往地址
     */
    @ApiModelProperty(value = "信件发往地址")
    private String address;
    /**
     * 信件收件人姓名
     */
    @ApiModelProperty(value = "信件收件人姓名")
    private String addressee;
    /**
     * 信件收信人手机号
     */
    @ApiModelProperty(value = "信件收信人手机号")
    private String phone;
    /**
     * 信件备注
     */
    @ApiModelProperty(value = "信件备注")
    private String remark;
    /**
     * 信件发送状态
     */
    @ApiModelProperty(value = "信件发送状态")
    private LetterState state;
    /**
     * 信件类型
     */
    @ApiModelProperty(value = "信件类型")
    private Integer letterType;
}
