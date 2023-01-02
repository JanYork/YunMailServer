package cn.totime.data.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (YunUser)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class YunUser extends Model<YunUser> {
    private static final long serialVersionUID = -1352793478878198846L;

    /**
     * 用户逐渐ID(雪花ID)
     */
    @TableId
    private Long uId;
    /**
     * 用户账号
     */
    private String uName;
    /**
     * 用户密码
     */
    private String uPwd;
    /**
     * 用户手机号
     */
    private String uPhone;
    /**
     * 用户邮箱
     */
    private String uEmail;
    /**
     * 用户账户状态
     */
    private Integer uState;
    /**
     * 用户创建时间
     */
    private Date uCreationTime;
}