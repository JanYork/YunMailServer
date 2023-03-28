package net.totime.mail.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import net.totime.mail.entity.Mail;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.MailService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 邮件任务处理器
 * @since 1.0.0
 */
@Configuration
public class MailJobHandler {
    @Resource
    private MailService ms;

    @XxlJob("MailJobHandler")
    public ApiResponse<String> execute() {
        List<Mail> list = ms.list(
                new QueryWrapper<Mail>()
                        .eq("status", 0)
                        .eq("use_serve", 0)
                        .orderByAsc("go_to_time")
                        .gt("go_to_time", System.currentTimeMillis())
        );
        //TODO:发送邮件
        return ApiResponse.ok("邮件任务处理器");
    }

    @XxlJob("MailRemind")
    public ApiResponse<String> remind() {
        List<Mail> list = ms.list(
                new QueryWrapper<Mail>()
                        .eq("status", 0)
                        .eq("use_serve", 0)
                        .orderByAsc("go_to_time")
                        .lt("go_to_time", System.currentTimeMillis() - 24 * 60 * 60 * 1000)
        );
        //TODO:向管理员发送邮件提醒
        return ApiResponse.ok("邮件提醒处理器");
    }
}