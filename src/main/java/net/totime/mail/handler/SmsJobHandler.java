/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import net.totime.mail.entity.Message;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.MessageService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 短信任务处理器
 * @since 1.0.0
 */
@Configuration
public class SmsJobHandler {
    @Resource
    private MessageService ms;

    @XxlJob("SmsJobHandler")
    public ApiResponse<String> execute() {
        List<Message> list = ms.list(
                new LambdaQueryWrapper<Message>()
                        .eq(Message::getState, 0)
                        .orderByAsc(Message::getSendTime)
                        .gt(Message::getSendTime, System.currentTimeMillis())
        );
        //TODO:发送短信
        return ApiResponse.ok("短信任务处理器");
    }
}
