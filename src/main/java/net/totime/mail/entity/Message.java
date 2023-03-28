package net.totime.mail.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信任务表(Message)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Message extends Model<Message> {
    /**
     * 短信ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 短信内容
     */
    private String text;
    /**
     * 短信发送时间
     */
    private Date sendTime;
    /**
     * 短信创建时间
     */
    private Date createTime;
    /**
     * 短信是否匿名
     */
    private Integer isUnnamed;
    /**
     * 短信状态
     */
    private Integer state;
}