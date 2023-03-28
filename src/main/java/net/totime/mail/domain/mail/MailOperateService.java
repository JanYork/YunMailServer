package net.totime.mail.domain.mail;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.totime.mail.entity.Mail;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.MailService;
import net.totime.mail.service.UserService;
import net.totime.mail.vo.MailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
    public List<Mail> queryMailAfterNow(Integer page, Integer size) {
        return mailService.page(
                        new Page<>(page, size),
                        new QueryWrapper<Mail>().gt(GO_TO_TIME, System.currentTimeMillis()))
                .getRecords();
    }

    /**
     * 分页查询当前时间之前的邮件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    public List<Mail> queryMailBeforeNow(Integer page, Integer size) {
        return mailService.page(
                        new Page<>(page, size),
                        new QueryWrapper<Mail>().lt(GO_TO_TIME, System.currentTimeMillis()))
                .getRecords();
    }

    /**
     * 查询邮件详情
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
}