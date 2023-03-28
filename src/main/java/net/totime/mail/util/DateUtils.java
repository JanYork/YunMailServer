package net.totime.mail.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 时间日期工具类
 * @since 1.0.0
 */
public class DateUtils {
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}