package net.totime.mail.domain.mail;

import cn.dev33.satoken.stp.StpUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.template.QuickConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.totime.mail.dto.MailDTO;
import net.totime.mail.entity.Mail;
import net.totime.mail.enums.MailState;
import net.totime.mail.service.MailService;
import net.totime.mail.service.UserService;
import net.totime.mail.util.IdUtils;
import net.totime.mail.vo.MailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
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
    @Resource
    private CacheManager cacheManager;
    private  Cache<String, MailVO> cache;
    private static final Set<String> KEYS = new HashSet<>();
    private static final String GO_TO_TIME = "go_to_time";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String USER_SERVER_ENUM = "TENCENT";
    private static final String MAIL_CONTENT = "go_to";

    @PostConstruct
    public void init() {
        QuickConfig qc = QuickConfig.newBuilder("cache")
                .expire(Duration.ofSeconds(100))
                .cacheType(CacheType.BOTH)
                .syncLocal(true)
                .build();
        cache = cacheManager.getOrCreateCache(qc);
    }

    /**
     * 分页查询邮件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    @Cached(name = "mail:", key = "'page_'+#page+#size", expire = 3600)
    public List<MailVO> queryMail(Integer page, Integer size) {
        KEYS.add("page_" + page + size);
        return mailService.page(new Page<>(page, size))
                .getRecords().stream().map(mail -> {
                    MailVO mailVO = new MailVO();
                    BeanUtils.copyProperties(mail, mailVO);
                    mailVO.setUserName(userService.getById(mail.getUserId()).getName());
                    return mailVO;
                }).collect(Collectors.toList());
    }


    /**
     * 分页查询当前时间之后的邮件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    @Cached(name = "mail:", key = "'after_now_'+#page+#size", expire = 3600)
    public List<MailVO> queryMailAfterNow(Integer page, Integer size) {
        KEYS.add("after_now_" + page + size);
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
    @Cached(name = "mail:", key = "'before_now_'+#page+#size", expire = 3600)
    public List<MailVO> queryMailBeforeNow(Integer page, Integer size) {
        KEYS.add("before_now_" + page + size);
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
     * 分页查询允许公开展示的邮件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Mail}> 邮件列表
     */
    @Cached(name = "mail:", key = "'public_'+#page+#size", expire = 3600)
    public List<MailVO> queryMailByPublic(Integer page, Integer size) {
        KEYS.add("public_" + page + size);
        return mailService.page(
                        new Page<>(page, size),
                        new QueryWrapper<Mail>().eq("is_public", true))
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
    @Cached(name = "mail:", key = "'id_'+#id", expire = 3600)
    public MailVO queryMailById(String id) {
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
     * @param id   用户id
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link MailVO}> 邮件列表
     */
    @Cached(name = "mail:", key = "'user_id_'+#id+#page+#size", expire = 3600)
    public List<MailVO> queryMailByUserId(Long id, Integer page, Integer size) {
        KEYS.add("user_id_" + id + page + size);
        return mailService.page(
                new Page<>(page, size),
                new QueryWrapper<Mail>().eq(USER_ID_COLUMN, id)
        ).getRecords().stream().map(mail -> {
            MailVO mailVO = new MailVO();
            BeanUtils.copyProperties(mail, mailVO);
            mailVO.setUserName(userService.getById(mail.getUserId()).getName());
            return mailVO;
        }).collect(Collectors.toList());
    }

    /**
     * 根据发往邮箱查询邮件
     *
     * @param mail 发往邮箱
     * @return {@link Mail} 邮件详情
     */
    @Cached(name = "mail:", key = "'go_to_'+#mail+#page+#size", expire = 3600)
    public List<MailVO> queryMailByGoToMail(String mail, Integer page, Integer size) {
        KEYS.add("go_to_" + mail + page + size);
        return mailService.page(
                new Page<>(page, size),
                new QueryWrapper<Mail>().eq(MAIL_CONTENT, mail)
        ).getRecords().stream().map(mail1 -> {
            MailVO mailVO = new MailVO();
            BeanUtils.copyProperties(mail1, mailVO);
            mailVO.setUserName(userService.getById(mail1.getUserId()).getName());
            return mailVO;
        }).collect(Collectors.toList());
    }

    /**
     * 添加邮件
     *
     * @param mailDTO 邮件信息
     * @return {@link Boolean} 是否添加成功
     */
    public Boolean addMail(MailDTO mailDTO) {
        Mail mail = new Mail();
        BeanUtils.copyProperties(mailDTO, mail);
        mail.setUserId(Long.parseLong(StpUtil.getLoginId().toString()));
        mail.setMailId(IdUtils.getMailId());
        mail.setMailCreateTime(new Date());
        //如果使用腾讯云代发，则设置状态为待支付
        if (USER_SERVER_ENUM.equals(mail.getUseServe().name())) {
            mail.setState(MailState.REJECT);
            //TODO:调用支付创建订单
        }
        this.deleteCache();
        return mailService.save(mail);
    }

    /**
     * 删除缓存
     */
    public void deleteCache() {
        System.out.println(KEYS);
        cache.removeAll(KEYS);
        //清空缓存
        KEYS.clear();
    }
}