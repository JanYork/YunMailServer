package net.totime.mail.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 赞助信息表(Sponsor)表实体类
 *
 * @author JanYork
 * @since 2023-04-19 15:54:56
 */
@Data
public class SponsorDTO {
    /**
     * 赞助留言
     */
    private String sponsorSay;
    /**
     * 赞助金额
     */
    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "1", message = "太小气啦~，不要这个，哼！")
    private BigDecimal sponsorAmount;
}
