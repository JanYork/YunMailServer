package cn.totime.data.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (UserInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends Model<UserInfo> {
    private static final long serialVersionUID = -7144987268306171082L;

    /**
     * 用户信息表ID
     */
    @TableId(type = IdType.AUTO)
    private Integer infoId;
    /**
     * 用户ID
     */
    private Long uId;
    /**
     * 1 = 男、2 = 女
     */
    private Integer uGender;
    /**
     * 用户签名文案
     */
    private String uSignature;
    /**
     * 用户生日
     */
    private Date uBirthday;
    /**
     * 用户头像(有默认值)
     */
    private String uAvatar;
    /**
     * 用户省份位置
     */
    private String uLocation;
    /**
     * 用户专业名称
     */
    private String uProfessional;
    /**
     * 用户所属星座
     */
    private String uConstellation;
}