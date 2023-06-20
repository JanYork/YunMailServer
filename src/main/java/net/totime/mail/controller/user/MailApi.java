/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.domain.MailOperate;
import net.totime.mail.dto.MailChangeDTO;
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
import net.totime.mail.vo.MailVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
     * @param mailDTO 邮件信息
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
            Long second = getSecond(mailAiCheck.getGoToTime(), new Date());
            String uMail;
            if (mail.getIsYourself()) {
                uMail = mail.getGoTo();
            } else {
                User user = userService.getById(StpUtil.getLoginIdAsLong());
                uMail = user.getEmail();
                mailOperate.sendToSucceedMail(mail.getGoTo(), String.valueOf(mailAiCheck.getMailId()), mailAiCheck.getMailTitle(), second.toString(), mailAiCheck.getGoToTime());
            }
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
     * 邮件删除(软删除)
     *
     * @param mailId 邮件ID
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/delete")
    @ApiOperation("邮件软删除")
    public ApiResponse<Boolean> delete(@RequestParam @Valid @NotNull(message = "邮件ID不能为空") String mailId) {
        Mail mail = mailService.getById(mailId);
        if (null == mail) {
            return ApiResponse.fail(false).message("信件不存在");
        }
        if (mail.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            mail.setState(GlobalState.DELETED.getState());
            boolean update = mailService.updateById(mail);
            return update ? ApiResponse.ok(true).message("删除成功") : ApiResponse.fail(false).message("删除失败");
        }
        return ApiResponse.fail(false).message("信件不存在");
    }

    /**
     * 信件修改
     *
     * @param mailChangeDTO 邮件修改信息
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @PostMapping("/update")
    @ApiOperation("邮件修改")
    public ApiResponse<Boolean> change(@RequestBody @Valid MailChangeDTO mailChangeDTO) {
        Mail oldMail = mailService.getById(mailChangeDTO.getMailId());
        // 查询信件是否存在
        if (null == oldMail) {
            return ApiResponse.fail(false).message("信件不存在");
        }
        // 判断是否是自己的信件
        if (!oldMail.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            return ApiResponse.fail(false).message("信件不存在");
        }
        // 判断信件状态是否为待投递
        if (!oldMail.getState().equals(GlobalState.WAITING_FOR_DELIVERY.getState())) {
            return ApiResponse.fail(false).message("信件状态不可修改");
        }
        // 判断是否离投递时间小于48小时
        long betweenDay = DateUtil.between(oldMail.getGoToTime(), new Date(), DateUnit.HOUR);
        if (betweenDay < 48) {
            return ApiResponse.fail(false).message("离投递时间小于48小时");
        }
        User user = userService.getById(StpUtil.getLoginIdAsLong());
        String key = null;
        // 判断邮箱是否更改
        if (!oldMail.getGoTo().equals(mailChangeDTO.getGoTo())) {
            // 校验验证码
            String code;
            if (oldMail.getIsYourself()) {
                code = (String) rut.get(KeyType.NORMAL.getKey() + mailChangeDTO.getGoToTime());
                key = KeyType.NORMAL.getKey() + mailChangeDTO.getGoToTime();
            } else {
                // 获取当前登录用户手机号
                code = (String) rut.get(KeyType.NORMAL.getKey() + user.getEmail());
                key = KeyType.NORMAL.getKey() + user.getEmail();
            }
            if (StringUtils.isBlank(code) || !code.equals(mailChangeDTO.getMailCode())) {
                return ApiResponse.fail(false).message("验证码错误");
            }
        }
        // 重新走内容校验
        String checkMsg = aiHandler.mailChangeAiCheck(mailChangeDTO);
        if (StringUtils.isNotBlank(checkMsg)) {
            oldMail.setState(GlobalState.MANUAL_REVIEW_AGAIN.getState());
            oldMail.setAiCheckMsg(checkMsg);
        }
        // 删除验证码
        if (key != null) {
            rut.delete(key);
        }
        mapperFacade.map(mailChangeDTO, oldMail);
        boolean update = mailService.updateById(oldMail);
        return update ? ApiResponse.ok(true).message("修改成功") : ApiResponse.fail(false).message("修改失败");
    }

    /**
     * 邮件查询(根据ID)
     *
     * @param mailId 邮件ID
     * @return {@link ApiResponse}<{@link MailVO}>
     */
    @GetMapping("/query/{mailId}")
    @ApiOperation("邮件查询(根据ID)")
    public ApiResponse<MailVO> query(@PathVariable @Valid @NotNull(message = "邮件ID不能为空") String mailId) {
        Mail mail = mailService.getById(Long.parseLong(mailId));
        if (null == mail) {
            return ApiResponse.<MailVO>fail(null).message("邮件不存在");
        }
        if (!mail.getUserId().equals(StpUtil.getLoginIdAsLong())) {
            return ApiResponse.<MailVO>fail(null).message("邮件不存在");
        }
        if (mail.getState().equals(GlobalState.DELETED.getState())) {
            return ApiResponse.<MailVO>fail(null).message("邮件不存在");
        }
        return ApiResponse.ok(mapperFacade.map(mail, MailVO.class)).message("查询成功");
    }

    /**
     * 邮件查询(根据用户)
     *
     * @param page 页面
     * @param size 大小
     * @return {@link ApiResponse}<{@link List}<{@link MailVO}>>
     */
    @GetMapping("/query/{page}/{size}")
    @ApiOperation("邮件查询(根据用户)")
    public ApiResponse<List<MailVO>> query(
            @PathVariable @Valid @NotNull(message = "页码不能为空") Integer page,
            @PathVariable @Valid @NotNull(message = "页大小不能为空") Integer size) {
        List<Mail> mail = mailService.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<Mail>()
                        .eq(Mail::getUserId, StpUtil.getLoginIdAsLong())
                        .ne(Mail::getState, GlobalState.DELETED.getState())
                        .orderByDesc(Mail::getMailCreateTime)
        ).getRecords();
        return ApiResponse.ok(mapperFacade.mapAsList(mail, MailVO.class)).message("查询成功");
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
