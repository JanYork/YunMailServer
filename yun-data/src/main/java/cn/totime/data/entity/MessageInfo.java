package cn.totime.data.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (MessageInfo)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MessageInfo extends Model<MessageInfo> {
    private static final long serialVersionUID = -6160626482567737207L;

    /**
     * 短信服务自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer messageId;
    /**
     * 用户ID
     */
    private Long uId;
    /**
     * 短信内容
     */
    private String messageText;
    /**
     * 收信手机号
     */
    private String messagePhone;
    /**
     * 短信状态
     */
    private Integer messageState;
    /**
     * 短信发送日期
     */
    private Date messageSendTime;
    /**
     * 短信创建时间
     */
    private Date messageFromTime;
    /**
     * 短信发送状态
     */
    private Integer messagePutState;
    /**
     * 短信取信码
     */
    private String messageTakeCode;
}