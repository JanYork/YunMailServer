package cn.totime.data.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (LetterInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:37:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LetterInfo extends Model<LetterInfo> {
    private static final long serialVersionUID = 6219758168556512672L;

    /**
     * 信件自增ID
     */
    private Integer letterId;
    /**
     * 用户ID
     */
    private Long uId;
    /**
     * 信件类型
     */
    private Integer letterType;
    /**
     * 信件状态
     */
    private Integer letterState;
    /**
     * 信件内容
     */
    private String letterText;
    /**
     * 信件标题
     */
    private String letterTitle;
    /**
     * 信件发送给谁
     */
    private Integer letterWho;
    /**
     * 信件发送地址
     */
    private String letterAddress;
    /**
     * 信件发送日期
     */
    private Date letterSendTime;
    /**
     * 信件创建时间
     */
    private Date letterFromTime;
    /**
     * 信件收信人姓名
     */
    private String letterRecipientName;
    /**
     * 信件发信人姓名
     */
    private String letterSenderName;
    /**
     * 信件匿名状态
     */
    private Integer letterAnonymity;
    /**
     * 信件物流手机号
     */
    private String letterPhone;
    /**
     * 信件公开状态
     */
    private Integer letterPublicState;
    /**
     * 信件发送状态
     */
    private Integer letterPutState;
    /**
     * 信件解密密文
     */
    private String letterTakeCode;
}

