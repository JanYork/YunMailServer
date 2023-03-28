package net.totime.mail.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 邮件视图数据实体
 * @since 1.0.0
 */
@Data
public class MailVO implements Serializable {
    private static final long serialVersionUID = -6356097508458182036L;

    /**
     * 邮件唯一ID
     */
    @TableId
    private String mailId;
    /**
     * 邮件标题
     */
    private String mailTitle;
    /**
     * 邮件内容
     */
    private String mailContent;
    /**
     * 邮件创建时间
     */
    private Date mailCreateTime;
    /**
     * 发往地址
     */
    private String goTo;
    /**
     * 邮件是否公开
     */
    private Integer isPublic;
    /**
     * 邮件发送时间
     */
    private Date goToTime;
    /**
     * 邮件是否发给自己
     */
    private Integer isYourself;
    /**
     * 邮件发送使用的服务
     */
    private Integer useServe;
    /**
     * 邮件发送状态
     */
    private Integer state;
    /**
     * 用户名称
     */
    private String userName;

}