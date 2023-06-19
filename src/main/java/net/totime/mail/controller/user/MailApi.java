/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.domain.MailOperate;
import net.totime.mail.dto.MailDTO;
import net.totime.mail.entity.Mail;
import net.totime.mail.entity.User;
import net.totime.mail.enums.GlobalState;
import net.totime.mail.enums.KeyType;
import net.totime.mail.handler.BaiDuAiHandler;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.MailService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.CheckReturn;
import net.totime.mail.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * 邮件任务表(Mail)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-06-14 22:59:37
 */
@RestController
@RequestMapping("/api/mail")
@Api(tags = "[用户]邮件相关接口")
@Slf4j
public class MailApi {
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    @Resource
    private MailOperate mailOperate;
    @Resource
    private MapperFacade mapperFacade;
    @Resource
    private RedisUtil rut;
    @Resource
    private BaiDuAiHandler aiHandler;

    /**
     * 邮件投递
     *
     * @param mailDTO 信件信息
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delivery")
    @ApiOperation("邮件投递")
    public ApiResponse<String> delivery(@RequestBody @Valid MailDTO mailDTO) {
        CheckReturn<Mail> check = check(mailDTO);
        if (!check.getStatus()) {
            return ApiResponse.<String>fail(null).message(check.getMsg());
        }
        Mail mail = check.getValue();
        mail.setUserId(StpUtil.getLoginIdAsLong());
        mail.setMailCreateTime(new Date());
        Mail mailAiCheck = aiHandler.mailAiCheck(mail);
        boolean save = mailService.save(mailAiCheck);
        if (!save) {
            return ApiResponse.fail("系统异常");
        }
        if (mailAiCheck.getState().equals(GlobalState.WAITING_FOR_DELIVERY.getState())) {
            String uMail;
            if (mail.getIsYourself()) {
                uMail = mail.getGoTo();
            } else {
                User user = userService.getById(StpUtil.getLoginIdAsLong());
                uMail = user.getEmail();
                // TODO 通知收信人
            }
            Long second = getSecond(mailAiCheck.getGoToTime(), new Date());
            try {
                mailOperate.sendSucceedMail(uMail, String.valueOf(mailAiCheck.getMailId()), mailAiCheck.getMailTitle(), second.toString(), mailAiCheck.getGoToTime());
            } catch (Exception e) {
                log.error("邮件投递成功通知邮件发送失败", e);
            }
        } else {
            String uMail;
            if (mail.getIsYourself()) {
                uMail = mail.getGoTo();
            } else {
                User user = userService.getById(StpUtil.getLoginIdAsLong());
                uMail = user.getEmail();
            }
            mailOperate.sendAuditMail(uMail, String.valueOf(mailAiCheck.getMailId()));
        }
        return ApiResponse.ok("投递成功");
    }

    /**
     * 校验
     *
     * @param mailDTO 邮件
     */
    private CheckReturn<Mail> check(MailDTO mailDTO) {
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        if (user.getEmail() == null) {
            return CheckReturn.fail("请先绑定邮箱");
        }
        // 校验验证码
        String code;
        String key;
        if (mailDTO.getIsYourself()) {
            code = (String) rut.get(KeyType.NORMAL.getKey() + mailDTO.getGoTo());
            key = KeyType.NORMAL.getKey() + mailDTO.getGoTo();
        } else {
            code = (String) rut.get(KeyType.NORMAL.getKey() + user.getEmail());
            key = KeyType.NORMAL.getKey() + user.getEmail();
        }
        if (StringUtils.isBlank(code) || !code.equals(mailDTO.getMailCode())) {
            return CheckReturn.fail("验证码错误");
        }
        // 删除验证码
        rut.delete(key);
        return CheckReturn.ok(mapperFacade.map(mailDTO, Mail.class));
    }

    /**
     * 获取时间差
     *
     * @param date1 date1
     * @param date2 date2
     * @return long
     */
    private Long getSecond(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / 1000;
    }
}
