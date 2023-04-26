package net.totime.mail.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 短信任务表DTO
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@Data
public class MessageDTO {
    /**
     * 短信内容
     */
    private String text;
    /**
     * 短信发送时间
     */
    private Date sendTime;
    /**
     * 短信是否匿名
     */
    private Integer isUnnamed;
}
