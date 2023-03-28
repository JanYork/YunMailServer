package net.totime.mail.controller.mail;

import cn.dev33.satoken.annotation.SaCheckLogin;
import net.totime.mail.domain.mail.MailOperateService;
import net.totime.mail.dto.MailDTO;
import net.totime.mail.entity.Mail;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.vo.MailVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    public List<MailVO> queryMailAfterNow(@PathVariable Integer page) {
        return mos.queryMailAfterNow(page, SIZE);
    }

    /**
     * 分页查询当前时间之前的邮件
     *
     * @param page 当前页面
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    @GetMapping("/queryBeforeNow/{page}")
    public List<MailVO> queryMailBeforeNow(@PathVariable Integer page) {
        return mos.queryMailBeforeNow(page, SIZE);
    }

    /**
     * 根据邮件ID查询邮件
     *
     * @param id 邮件id
     * @return {@link Mail} 邮件详情
     */
    @PostMapping("/queryById")
    public ApiResponse<MailVO> queryMailDetail(@PathParam("id") Long id) {
        return ApiResponse.ok(mos.queryMailById(id));
    }

    /**
     * 根据用户ID查询邮件
     *
     * @param id 用户id
     * @return {@link Mail} 邮件详情
     */
    @PostMapping("/queryByUserId")
    public ApiResponse<List<MailVO>> queryMailByUserId(@PathParam("id") Long id) {
        return ApiResponse.ok(mos.queryMailByUserId(id));
    }

    /**
     * 分页查询允许公开展示的邮件
     *
     * @param page 当前页面
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    @GetMapping("/queryPublic/{page}")
    public List<MailVO> queryPublicMail(@PathVariable Integer page) {
        return mos.queryMailByPublic(page, SIZE);
    }

    /**
     * 新增邮件(自动绑定用户)
     *
     * @param mailDTO 邮件信息
     * @return {@link ApiResponse}<{@link Mail}> 邮件信息
     */
    @PostMapping("/add")
    @SaCheckLogin
    public ApiResponse<Boolean> addMail(@Valid @RequestBody MailDTO mailDTO) {
        return ApiResponse.ok(mos.addMail(mailDTO));
    }
}