package net.totime.mail.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import net.totime.mail.enums.LetterState;

import java.util.Date;

/**
 * 时光信件表(Letter)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@Data
public class LetterVO {
    /**
     * 信件唯一ID
     */
    @TableId
    private String letterId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 信件标题
     */
    private String letterTitle;
    /**
     * 信件内容
     */
    private String letterContent;
    /**
     * 信件创建时间
     */
    private Date letterCreateTime;
    /**
     * 信件是否公开
     */
    private Boolean isPublic;
    /**
     * 信件发送时间
     */
    private Date goToTime;
    /**
     * 信件是否发给自己
     */
    private Boolean isYourself;
    /**
     * 信件发往地址
     */
    private String address;
    /**
     * 信件收件人姓名
     */
    private String addressee;
    /**
     * 信件收信人手机号
     */
    private String phone;
    /**
     * 信件备注
     */
    private String remark;
    /**
     * 信件发送状态
     */
    private LetterState state;
    /**
     * 信件类型
     */
    private Integer letterType;
}