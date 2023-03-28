package net.totime.mail.domain.letter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.totime.mail.entity.Letter;
import net.totime.mail.service.LetterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private static final String GO_TO_TIME = "go_to_datetime";

    /**
     * 分页查询信件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Letter}> 信件列表
     */
    public List<Letter> queryLetter(Integer page, Integer size) {
        return letter.page(new Page<>(page, size)).getRecords();
    }

    /**
     * 分页查询当前时间之后的信件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Letter}> 信件列表
     */
    public List<Letter> queryLetterAfterNow(Integer page, Integer size) {
        return letter.page(
                        new Page<>(page, size),
                        new QueryWrapper<Letter>().gt(GO_TO_TIME, System.currentTimeMillis()))
                .getRecords();
    }

    /**
     * 分页查询当前时间之前的信件
     *
     * @param page 当前页面
     * @param size 分页大小
     * @return {@link List}<{@link Letter}> 信件列表
     */
    public List<Letter> queryLetterBeforeNow(Integer page, Integer size) {
        return letter.page(
                        new Page<>(page, size),
                        new QueryWrapper<Letter>().lt(GO_TO_TIME, System.currentTimeMillis()))
                .getRecords();
    }
}