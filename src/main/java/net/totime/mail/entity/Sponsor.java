package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 赞助信息表(Sponsor)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Sponsor extends Model<Sponsor> {
    /**
     * 赞助表ID
     */
    private Integer id;
    /**
     * 赞助用户
     */
    private Long userId;
    /**
     * 赞助名称
     */
    private String sponsorName;
    /**
     * 赞助留言
     */
    private String sponsorSay;
    /**
     * 赞助金额
     */
    private Double sponsorAmount;
    /**
     * 赞助邮箱
     */
    private String sponsorMail;
}