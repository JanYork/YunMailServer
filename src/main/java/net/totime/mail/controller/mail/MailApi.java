package net.totime.mail.controller.mail;

import net.totime.mail.domain.mail.MailOperateService;
import net.totime.mail.entity.Mail;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.MailService;
import net.totime.mail.vo.MailVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 云寄邮件服务接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/mail")
public class MailApi {
    @Resource
    private MailOperateService mos;
    @Resource
    private MailService mailService;
    private static final Integer SIZE = 8;

    /**
     * 分页查询邮件
     *
     * @param page 当前页面
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    @GetMapping("/query/{page}")
    public List<Mail> queryMail(@PathVariable Integer page) {
        return mos.queryMail(page, SIZE);
    }

    /**
     * 分页查询当前时间之后的邮件
     *
     * @param page 当前页面
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    @GetMapping("/queryAfterNow/{page}")
    public List<Mail> queryMailAfterNow(@PathVariable Integer page) {
        return mos.queryMailAfterNow(page, SIZE);
    }

    /**
     * 分页查询当前时间之前的邮件
     *
     * @param page 当前页面
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    @GetMapping("/queryBeforeNow/{page}")
    public List<Mail> queryMailBeforeNow(@PathVariable Integer page) {
        return mos.queryMailBeforeNow(page, SIZE);
    }

    /**
     * 查询邮件详情
     *
     * @param id 邮件id
     * @return {@link Mail} 邮件详情
     */
    @PostMapping("/queryById")
    public ApiResponse<MailVO> queryMailDetail(@PathParam("id") Long id) {
        return ApiResponse.ok(mos.queryMailById(id));
    }
}