package net.totime.mail.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/05/11
 * @description 快递助手订阅返回数据
 * @since 1.0.0
 */
@Data
@Builder
public class CourierSubVO {
    private String traceId;
    private String isSub;
}
