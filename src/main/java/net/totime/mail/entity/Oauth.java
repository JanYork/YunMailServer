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
 * 用户第三方授权信息
 *
 * @author JanYork
 * @since 2023-06-14 22:59:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "用户第三方授权信息", description = "字段与数据库一致")
public class Oauth extends Model<Oauth> {
    private static final long serialVersionUID = -358948274184984899L;
    /**
     * 第三方授权表ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "第三方授权表ID", example = "1")
    private Integer id;
    /**
     * 第三方授权绑定的用户ID
     */
    @ApiModelProperty(value = "第三方授权绑定的用户ID", example = "1", required = true)
    @NotNull(message = "用户不能为空")
    private Long userId;
    /**
     * 第三方授权类型
     */
    @ApiModelProperty(value = "第三方授权类型", example = "1", required = true)
    @NotNull(message = "类型不能为空")
    private Integer provider;
    /**
     * 第三方授权唯一标识
     */
    @ApiModelProperty(value = "第三方授权唯一标识", example = "2389489723", required = true)
    @NotNull(message = "唯一标识不能为空")
    private String openId;
    /**
     * 第三方授权Token
     */
    @ApiModelProperty(value = "第三方授权Token", example = "sasgrhtjeyr75js64wsj6")
    private String accessToken;
    /**
     * 第三方授权长Token
     */
    @ApiModelProperty(value = "第三方授权长Token", example = "sasgrhtjeyr75js64wsj6")
    private String refreshToken;
    /**
     * 第三方授权Token时长
     */
    @ApiModelProperty(value = "第三方授权Token时长", example = "7200")
    private Integer tokenExpiry;
    /**
     * 第三方授权创建时间
     */
    @ApiModelProperty(value = "第三方授权创建时间", example = "2023-06-14 22:59:43")
    private Date createdAt;
    /**
     * 第三方授权更新时间
     */
    @ApiModelProperty(value = "第三方授权更新时间", example = "2023-06-14 22:59:43")
    private Date updatedAt;
}
