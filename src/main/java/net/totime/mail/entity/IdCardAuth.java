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

/**
 * 实名认证信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "实名认证信息", description = "字段与数据库一致")
public class IdCardAuth extends Model<IdCardAuth> {
    private static final long serialVersionUID = -2476911204936312004L;
    /**
     * 实名认证ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "实名认证ID", example = "1")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "1", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "12345678901", required = true)
    @NotNull(message = "手机号不能为空")
    private String phone;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", example = "123456789012345678", required = true)
    @NotNull(message = "身份证号不能为空")
    private String idCard;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "张三", required = true)
    @NotNull(message = "姓名不能为空")
    private String name;
    /**
     * 其他证明身份图片[JSON]
     */
    @ApiModelProperty(value = "其他证明身份图片[JSON]", example = "[{id:1,url:https://xxx.com/xxx.jpg}]")
    private String other;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", example = "1", required = true)
    @NotNull(message = "状态不能为空")
    private Boolean status;
}
