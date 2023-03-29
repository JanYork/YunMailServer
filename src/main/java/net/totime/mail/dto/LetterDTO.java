package net.totime.mail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import net.totime.mail.annotation.TimeDistance;
import net.totime.mail.enums.LetterState;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时光信件表(Letter)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:34
 */
@Data
public class LetterDTO {
    /**
     * 信件标题
     */
    @NotBlank
    @Size(min = 1, max = 30, message = "标题长度为1-30个字符")
    private String letterTitle;
    /**
     * 信件内容
     */
    @NotBlank
    @Size(min = 200, max = 5000, message = "最小长度为200个字符")
    private String letterContent;
    /**
     * 信件是否公开
     */
    @NotBlank
    private Boolean isPublic;
    /**
     * 信件发送时间
     */
    @NotNull
    @Future(message = "发送时间必须大于当前时间")
    @TimeDistance(distance = 1, unit = ChronoUnit.MONTHS, message = "时间必须大于1个月")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date goToTime;
    /**
     * 信件是否发给自己
     */
    @NotNull
    private Boolean isYourself;
    /**
     * 信件发往地址
     */
    @NotBlank
    private String address;
    /**
     * 信件收件人姓名
     */
    @NotBlank
    @Size(min = 2, max = 6, message = "收件人姓名长度为2-6个字符")
    private String addressee;
    /**
     * 信件收信人手机号
     */
    @NotBlank
    @Length(min = 11, max = 11, message = "手机号长度为11位")
    @Pattern(regexp = "^1[3|4|5|7|8][0-9]\\d{8}$", message = "手机号格式不正确")
    private String phone;
    /**
     * 信件备注
     */
    @Size(max = 100, message = "备注长度不能超过100个字符")
    private String remark;
    /**
     * 信件发送状态
     */
    @NotNull
    private LetterState state;
    /**
     * 信件类型
     */
    @NotNull
    @Min(value = 1, message = "类型错误")
    @Max(value = 10, message = "类型错误")
    private Integer letterType;
}