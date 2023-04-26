package net.totime.mail.util;

import cn.hutool.core.util.RandomUtil;
import net.totime.mail.enums.PayState;
import net.totime.mail.enums.PayType;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/14
 * @description 订单号生成工具类
 * @since 1.0.0
 */
public class OrderNumberUtil {
    /**
     * 生成赞助订单号
     *
     * @return {@link Long}
     */
    public static Long createSponsorOrderNumber(PayType type) {
        return Long.parseLong(System.currentTimeMillis() + RandomUtil.randomNumbers(4) + type.getId());
    }
}
