package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class IdCardAuth extends Model<IdCardAuth> {
    /**
     * 实名认证ID
     */
    private Integer id;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 姓名
     */
    private String name;
    /**
     * 其他证明身份图片[JSON]
     */
    private String other;
}