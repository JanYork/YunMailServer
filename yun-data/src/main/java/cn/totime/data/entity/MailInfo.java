package cn.totime.data.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (MailInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MailInfo extends Model<MailInfo> {
    private static final long serialVersionUID = 3718169349598683655L;

    /**
     * 邮件信息自增ID
     */
    private Integer mailId;
    /**
     * 用户ID
     */
    private Long uId;
    /**
     * 邮件状态
     */
    private Integer mailState;
    /**
     * 邮件内容
     */
    private String mailText;
    /**
     * 邮件标题
     */
    private String mailTitle;
    /**
     * 邮件发送给谁
     */
    private Integer mailWho;
    /**
     * 邮件发送日期
     */
    private Date mailSendTime;
    /**
     * 邮件创建时间
     */
    private Date mailFromTime;
    /**
     * 邮件收件邮箱
     */
    private String mailRecipientEmail;
    /**
     * 邮件匿名状态
     */
    private Integer mailAnonymity;
    /**
     * 邮件公开状态
     */
    private Integer mailPublicState;
    /**
     * 邮件发送状态
     */
    private Integer mailPutState;
    /**
     * 邮件解密密文
     */
    private String mailTakeCode;
}