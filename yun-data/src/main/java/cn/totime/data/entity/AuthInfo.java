package cn.totime.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (AuthInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:25:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthInfo extends Model<AuthInfo> {
    private static final long serialVersionUID = 5251901183866895389L;

    /**
     * 第三方授权表自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer authId;
    /**
     * 用户ID
     */
    private Long uId;
    /**
     * 第三方授权类型
     */
    private String authType;
    /**
     * 第三方授权唯一标识ID
     */
    private String authOpenid;
    /**
     * 第三方授权令牌
     */
    private String accessToken;
}

