package net.totime.mail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.totime.mail.annotation.TimeDistance;
import net.totime.mail.enums.MailUseServer;

import javax.validation.constraints.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 邮件数据传输对象
 * @since 1.0.0
 */
@Data
public class MailDTO {
    /**
     * 邮件标题
     */
    @NotBlank
    @Size(min = 1, max = 30, message = "标题长度为1-30个字符")
    private String mailTitle;
    /**
     * 邮件内容
     */
    @NotBlank
    @Size(min = 20, max = 5000, message = "最小长度为20个字符")
    private String mailContent;
    /**
     * 发往地址
     */
    @NotBlank
    @Email
    private String goTo;
    /**
     * 邮件是否公开
     */
    @NotNull
    private Boolean isPublic;
    /**
     * 邮件发送时间
     */
    @NotNull
    @Future(message = "发送时间必须大于当前时间")
    @TimeDistance(distance = 5, unit = ChronoUnit.DAYS)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date goToTime;
    /**
     * 邮件是否发给自己
     */
    @NotNull
    private Boolean isYourself;
    /**
     * 邮件发送使用的服务
     */
    @NotNull
    private MailUseServer useServe;
}