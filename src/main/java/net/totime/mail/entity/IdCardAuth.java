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
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实名认证表(IdCardAuth)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "实名信息", description = "实名信息表")
@ApiSupport(author = "JanYork")
public class IdCardAuth extends Model<IdCardAuth> {
    private static final long serialVersionUID = -6531555181975472122L;
    /**
     * 实名认证ID
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "实名认证ID", example = "10000")
    private Integer id;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号", example = "430****199901010101", required = true)
    private String idCard;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "张三", required = true)
    private String name;
    /**
     * 其他证明身份图片[JSON]
     */
    @ApiModelProperty(value = "其他证明身份图片[JSON]", required = true, example = "[{\"id\":1,\"url\":\"http://www.baidu.com\"},{\"id\":2,\"url\":\"http://www.baidu.com\"}]")
    private String other;
}
