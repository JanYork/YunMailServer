package net.totime.mail.domain.mail;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.totime.mail.entity.Mail;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.MailService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.IdUtils;
import net.totime.mail.vo.MailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 邮件服务业务实现
 * @since 1.0.0
 */
@Service
public class MailOperateService {
    @Resource
    private MailService mailService;
    @Resource
    private UserService userService;
    private static final String GO_TO_TIME = "go_to_datetime";
    private static final String USER_ID_COLUMN = "user_id";

    /**
     * 分页查询邮件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    public List<Mail> queryMail(Integer page, Integer size) {
        return mailService.page(new Page<>(page, size)).getRecords();
    }


    /**
     * 分页查询当前时间之后的邮件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    public List<MailVO> queryMailAfterNow(Integer page, Integer size) {
        return mailService.page(
                        new Page<>(page, size),
                        new QueryWrapper<Mail>().gt(GO_TO_TIME, System.currentTimeMillis()))
                .getRecords().stream().map(mail -> {
                    MailVO mailVO = new MailVO();
                    BeanUtils.copyProperties(mail, mailVO);
                    mailVO.setUserName(userService.getById(mail.getUserId()).getName());
                    return mailVO;
                }).collect(Collectors.toList());
    }

    /**
     * 分页查询当前时间之前的邮件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    public List<MailVO> queryMailBeforeNow(Integer page, Integer size) {
        return mailService.page(
                        new Page<>(page, size),
                        new QueryWrapper<Mail>().lt(GO_TO_TIME, System.currentTimeMillis()))
                .getRecords().stream().map(mail -> {
                    MailVO mailVO = new MailVO();
                    BeanUtils.copyProperties(mail, mailVO);
                    mailVO.setUserName(userService.getById(mail.getUserId()).getName());
                    return mailVO;
                }).collect(Collectors.toList());
    }

    /**
     * 根据邮件ID查询邮件
     *
     * @param id 邮件id
     * @return {@link Mail} 邮件详情
     */
    public MailVO queryMailById(Long id) {
        Mail mail = mailService.getById(id);
        Optional.ofNullable(mail).orElseThrow(() -> new RuntimeException("邮件不存在"));
        MailVO mailVO = new MailVO();
        BeanUtils.copyProperties(mail, mailVO);
        mailVO.setUserName(userService.getById(mail.getUserId()).getName());
        return mailVO;
    }

    /**
     * 根据用户ID查询邮件
     *
     * @param id 用户id
     * @return {@link Mail} 邮件详情
     */
    public List<MailVO> queryMailByUserId(Long id) {
        return mailService.list(new QueryWrapper<Mail>().eq(USER_ID_COLUMN, id)).stream().map(mail -> {
            MailVO mailVO = new MailVO();
            BeanUtils.copyProperties(mail, mailVO);
            mailVO.setUserName(userService.getById(mail.getUserId()).getName());
            return mailVO;
        }).collect(Collectors.toList());
    }

    /**
     * 添加邮件
     *
     * @param mailDTO 邮件信息
     * @return {@link Boolean} 是否添加成功
     */
    public Boolean addMail(Mail mailDTO) {
        Mail mail = new Mail();
        BeanUtils.copyProperties(mailDTO, mail);
        mail.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        mail.setMailId(IdUtils.getMailId());
        mail.setMailCreateTime(new Date());
        //如果使用腾讯云代发，则设置状态为待支付
        if (mail.getUseServe() == 1) {
            mail.setState(3);
            //TODO: 调用支付订单创建
        }
        return mailService.save(mail);
    }
}