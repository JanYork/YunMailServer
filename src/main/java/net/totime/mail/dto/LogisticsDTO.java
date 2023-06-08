/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 信件物流DTO
 *
 * @author JanYork
 * @since 2023-05-12 15:27:22
 */
@Data
public class LogisticsDTO {
    /**
     * 信件ID
     */
    private String letterId;
    /**
     * 物流单号
     */
    private String logisticsId;
    /**
     * 物流商户['SF','YZ','YT'...]
     */
    private String logisticsCode;
}
