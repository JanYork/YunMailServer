package net.totime.mail.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/26
 * @description 基于Hutool的分布式ID生成工具
 * @see IdUtil
 * @since 1.0.0
 */
public class SnowflakeUtil {
    /**
     * 雪花ID对象
     */
    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

    /**
     * 获取雪花ID
     *
     * @return 雪花ID
     */
    public static long getSnowflakeId() {
        return SNOWFLAKE.nextId();
    }
}