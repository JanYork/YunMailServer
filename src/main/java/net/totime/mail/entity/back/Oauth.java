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
import net.totime.mail.enums.OauthType;

import java.util.Date;

/**
 * 用户第三方授权表(Oauth)表实体类
 *
 * @author JanYork
 * @since 2023-04-02 10:38:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "第三方授权信息", description = "第三方授权信息表")
@ApiSupport(author = "JanYork")
public class Oauth extends Model<Oauth> {
    private static final long serialVersionUID = 8757679743409972743L;
    /**
     * 第三方授权表ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "第三方授权表ID")
    private Integer id;
    /**
     * 第三方授权绑定的用户ID
     */
    @ApiModelProperty(value = "第三方授权绑定的用户ID")
    private Long userId;
    /**
     * 第三方授权类型
     */
    @ApiModelProperty(value = "第三方授权类型")
    private OauthType provider;
    /**
     * 第三方授权唯一标识
     */
    @ApiModelProperty(value = "第三方授权唯一标识")
    private String openId;
    /**
     * 第三方授权Token
     */
    @ApiModelProperty(value = "第三方授权唯一标识")
    private String accessToken;
    /**
     * 第三方授权长Token
     */
    @ApiModelProperty(value = "第三方授权唯一标识")
    private String refreshToken;
    /**
     * 第三方授权Token时长
     */
    @ApiModelProperty(value = "第三方授权Token时长")
    private Integer tokenExpiry;
    /**
     * 第三方授权创建时间
     */
    @ApiModelProperty(value = "第三方授权创建时间")
    private Date createdAt;
    /**
     * 第三方授权更新时间
     */
    @ApiModelProperty(value = "第三方授权更新时间")
    private Date updatedAt;
}
