package cn.totime.api.data;

import cn.totime.common.vo.ResultVO;
import cn.totime.data.entity.LetterInfo;
import cn.totime.data.service.impl.LetterInfoServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (LetterInfo)表控制层
 *
 * @author JanYork
 * @since 2023-01-02 13:15:27
 */
@RestController
@RequestMapping("letter")
@Api(tags = "实体信件接口")
@ApiSupport(author = "JanYork")
public class LetterInfoController {
    @Autowired
    private LetterInfoServiceImpl letterInfoServiceImpl;

    /**
     * 查询所有数据{@link cn.totime.data.entity.LetterInfo}
     *
     * @return 对应数据集合
     */
    @Deprecated
    @RequestMapping("select/all")
    @ApiOperation(value = "查询所有数据", notes = "查询实体信件所有数据")
    @Cacheable(value = "all-letter-info", key = "#root.methodName")
    public ResultVO<List<LetterInfo>> selectAll() {
        return new ResultVO<List<LetterInfo>>().success("查询成功", letterInfoServiceImpl.list());
    }

    /**
     * 通过主键查询单条数据{@link cn.totime.data.entity.LetterInfo}
     *
     * @param id 主键
     * @return 对应数据
     */
    @RequestMapping("select/{id}")
    @ApiOperation(value = "查询单条数据", notes = "通过主键查询实体信件单条数据")
    @Cacheable(value = "letter-info", key = "#id")
    public ResultVO<LetterInfo> select(@PathVariable("id") Long id) {
        return new ResultVO<LetterInfo>().success("查询成功", letterInfoServiceImpl.getById(id));
    }

    /**
     * 查询用户所有数据{@link cn.totime.data.entity.LetterInfo}
     *
     * @param userId 用户id
     * @return 对应数据集合
     */
    @Deprecated
    @RequestMapping("select/user/{userId}")
    @ApiOperation(value = "查询用户所有数据", notes = "通过用户ID查询实体信件单条数据")
    @Cacheable(value = "all-letter-info-by-uid", key = "#userId")
    public ResultVO<List<LetterInfo>> selectByUserId(@PathVariable("userId") Long userId) {
        return new ResultVO<List<LetterInfo>>().success("查询成功", letterInfoServiceImpl.list(
                        new QueryWrapper<LetterInfo>().eq("u_id", userId)
                )
        );
    }

    /**
     * 查询用户最近一封信数据{@link cn.totime.data.entity.LetterInfo}
     *
     * @param userId 用户id
     * @return 对应数据
     */
    @RequestMapping("select/user/recent/{userId}")
    @ApiOperation(value = "查询最近的一条数据", notes = "通过用户ID查询实体信件最近的一条信件数据")
    @Cacheable(value = "recent-letter-info-by-uid", key = "#userId")
    public ResultVO<LetterInfo> selectRecentByUserId(@PathVariable("userId") Long userId) {
        return new ResultVO<LetterInfo>().success("查询成功", letterInfoServiceImpl.getOne(
                        new QueryWrapper<LetterInfo>().eq("u_id", userId).orderByDesc("letter_from_time")
                )
        );
    }

    /**
     * 新增一封信件{@link cn.totime.data.entity.LetterInfo}
     *
     * @param letterInfo 信件实体
     * @return 对应数据
     */
    @RequestMapping("insert")
    @ApiOperation(value = "新增数据", notes = "新增一封信件")
    @CachePut(value = "letter-info", key = "#letterInfo.getLetterId()")
    public ResultVO<Boolean> insert(LetterInfo letterInfo) {
        return new ResultVO<Boolean>().success("新增成功", letterInfoServiceImpl.save(letterInfo));
    }

    /**
     * 修改一封信件{@link cn.totime.data.entity.LetterInfo}
     *
     * @param letterInfo 信件实体
     * @return 对应数据
     */
    @RequestMapping("update")
    @ApiOperation(value = "修改数据", notes = "修改一封信件")
    @CachePut(value = "letter-info", key = "#letterInfo.getLetterId()")
    public ResultVO<Boolean> update(LetterInfo letterInfo) {
        return new ResultVO<Boolean>().success("修改成功", letterInfoServiceImpl.updateById(letterInfo));
    }

    /**
     * 删除一封信件{@link cn.totime.data.entity.LetterInfo}
     *
     * @param id 信件id
     * @return 对应数据
     */
    @RequestMapping("delete/{id}")
    @ApiOperation(value = "删除数据", notes = "删除一封信件")
    @CacheEvict(value = "letter-info", key = "#id")
    public ResultVO<Boolean> delete(@PathVariable("id") Long id) {
        return new ResultVO<Boolean>().success("删除成功", letterInfoServiceImpl.removeById(id));
    }
}