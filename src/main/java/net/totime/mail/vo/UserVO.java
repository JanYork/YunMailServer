package net.totime.mail.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/01
 * @description 用户视图数据实体
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = -5602808791804827752L;
    /**
     * 用户自增ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    @TableField("`name`")
    private String name;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户创建时间
     */
    private Date createTime;
}