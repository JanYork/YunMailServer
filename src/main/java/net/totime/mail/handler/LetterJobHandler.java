/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import net.totime.mail.entity.back.Letter;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.LetterService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 信件任务处理器
 * @since 1.0.0
 */
@Configuration
public class LetterJobHandler {
    @Resource
    private LetterService ls;

    @XxlJob("LetterJobHandler")
    public ApiResponse<String> execute() {
        List<Letter> list = ls.list(
                new QueryWrapper<Letter>()
                        .eq("status", 0)
                        .orderByAsc("go_to_time")
                        .gt("go_to_time", System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        );
        //TODO:发送信件
        return ApiResponse.ok("信件任务处理器");
    }

    @XxlJob("LetterRemind")
    public ApiResponse<String> remind() {
        List<Letter> list = ls.list(
                new QueryWrapper<Letter>()
                        .eq("status", 0)
                        .orderByAsc("go_to_time")
                        .lt("go_to_time", System.currentTimeMillis())
        );
        //TODO:发送提醒
        return ApiResponse.ok("信件当日提醒处理器");
    }
}
