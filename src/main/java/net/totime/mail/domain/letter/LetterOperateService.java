package net.totime.mail.domain.letter;

import cn.dev33.satoken.stp.StpUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.totime.mail.dto.LetterDTO;
import net.totime.mail.entity.Letter;
import net.totime.mail.enums.LetterState;
import net.totime.mail.service.LetterService;
import net.totime.mail.util.IdUtils;
import net.totime.mail.vo.LetterVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 信件操作服务
 * @since 1.0.0
 */
@Service
public class LetterOperateService {
    @Resource
    private LetterService letter;
    private static final String GO_TO_TIME = "go_to_time";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String PHONE = "phone";

    /**
     * 分页查询信件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Letter}> 信件列表
     */
    public List<LetterVO> queryLetter(Integer page, Integer size) {
        return letter.page(new Page<>(page, size))
                .getRecords().stream().map(letter -> {
                    LetterVO letterVO = new LetterVO();
                    BeanUtils.copyProperties(letter, letterVO);
                    return letterVO;
                }).collect(Collectors.toList());
    }

    /**
     * 分页查询当前时间之后的信件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Letter}> 信件列表
     */
    public List<LetterVO> queryLetterAfterNow(Integer page, Integer size) {
        return letter.page(
                        new Page<>(page, size),
                        new QueryWrapper<Letter>().gt(GO_TO_TIME, System.currentTimeMillis()))
                .getRecords().stream().map(letter -> {
                    LetterVO letterVO = new LetterVO();
                    BeanUtils.copyProperties(letter, letterVO);
                    return letterVO;
                }).collect(Collectors.toList());
    }

    /**
     * 分页查询当前时间之前的信件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Letter}> 信件列表
     */
    public List<LetterVO> queryLetterBeforeNow(Integer page, Integer size) {
        return letter.page(
                        new Page<>(page, size),
                        new QueryWrapper<Letter>().lt(GO_TO_TIME, System.currentTimeMillis()))
                .getRecords().stream().map(letter -> {
                    LetterVO letterVO = new LetterVO();
                    BeanUtils.copyProperties(letter, letterVO);
                    return letterVO;
                }).collect(Collectors.toList());
    }

    /**
     * 分页查询允许公开展示的邮件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Letter}> 信件列表
     */
    public List<LetterVO> queryAllowPublic(Integer page, Integer size) {
        return letter.page(
                        new Page<>(page, size),
                        new QueryWrapper<Letter>().eq("is_public", true))
                .getRecords().stream().map(letter -> {
                    LetterVO letterVO = new LetterVO();
                    BeanUtils.copyProperties(letter, letterVO);
                    return letterVO;
                }).collect(Collectors.toList());
    }

    /**
     * 根据信件ID查询信件
     *
     * @param id 信件ID
     * @return {@link LetterVO} 信件
     */
    public LetterVO queryLetterById(Long id) {
        Letter letter = this.letter.getById(id);
        LetterVO letterVO = new LetterVO();
        BeanUtils.copyProperties(letter, letterVO);
        return letterVO;
    }

    /**
     * 根据用户ID查询信件
     *
     * @param id   用户id
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link LetterVO}> 信件列表
     */
    public List<LetterVO> queryLetterByUserId(Long id, Integer page, Integer size) {
        return letter.page(
                        new Page<>(page, size),
                        new QueryWrapper<Letter>().eq(USER_ID_COLUMN, id))
                .getRecords().stream().map(letter -> {
                    LetterVO letterVO = new LetterVO();
                    BeanUtils.copyProperties(letter, letterVO);
                    return letterVO;
                }).collect(Collectors.toList());
    }

    /**
     * 根据收件人手机号查询信件
     *
     * @param phone 收件人手机号
     * @param page  当前页面
     * @param size  分页大小
     * @return {@link List}<{@link LetterVO}> 信件列表
     */
    public List<LetterVO> queryLetterByPhone(String phone, Integer page, Integer size) {
        return letter.page(
                        new Page<>(page, size),
                        new QueryWrapper<Letter>().eq(PHONE, phone))
                .getRecords().stream().map(letter -> {
                    LetterVO letterVO = new LetterVO();
                    BeanUtils.copyProperties(letter, letterVO);
                    return letterVO;
                }).collect(Collectors.toList());
    }

    /**
     * 添加信件
     *
     * @param letterDTO 信件
     * @return {@link Boolean} 是否添加成功
     */
    public Boolean addLetter(LetterDTO letterDTO) {
        Letter letter = new Letter();
        BeanUtils.copyProperties(letterDTO, letter);
        letter.setUserId((Long.parseLong(StpUtil.getLoginId().toString())));
        letter.setLetterId(IdUtils.getLetterId());
        letter.setLetterCreateTime(new Date());
        letter.setState(LetterState.REJECT);
        //TODO: 调用支付创建订单
        return this.letter.save(letter);
    }
}