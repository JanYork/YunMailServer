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

/**
 * 许愿表(Wish)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "许愿信息", description = "许愿信息表")
@ApiSupport(author = "JanYork")
public class Wish extends Model<Wish> {
    private static final long serialVersionUID = 4251062567217992976L;
    /**
     * 愿望ID
     */
    @TableId
    @ApiModelProperty(value = "愿望ID")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 愿望
     */
    @ApiModelProperty(value = "愿望")
    private String text;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String image;
    /**
     * 祝福
     */
    @ApiModelProperty(value = "祝福")
    private Integer blessing;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date creatTime;
}
