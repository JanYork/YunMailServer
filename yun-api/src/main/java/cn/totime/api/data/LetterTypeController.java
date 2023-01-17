package cn.totime.api.data;

import cn.totime.common.vo.ResultVO;
import cn.totime.data.entity.LetterType;
import cn.totime.data.service.impl.LetterTypeServiceImpl;
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
 * (LetterType)表控制层
 *
 * @author JanYork
 * @since 2023-01-02 13:15:28
 */
@RestController
@RequestMapping("letterType")
@Api(tags = "信件类型接口")
@ApiSupport(author = "JanYork")
public class LetterTypeController {
    @Autowired
    private LetterTypeServiceImpl letterTypeServiceImpl;

    /**
     * 查询所有数据{@link cn.totime.data.entity.LetterType}
     *
     * @return 对应数据集合
     */
    @Deprecated
    @RequestMapping("select/all")
    @ApiOperation(value = "查询所有数据", notes = "查询所有信件类型")
    @Cacheable(value = "all-letter-type", key = "#root.methodName")
    public ResultVO<List<LetterType>> selectAll() {
        return new ResultVO<List<LetterType>>().success("查询成功", letterTypeServiceImpl.list());
    }

    /**
     * 通过主键查询单条数据{@link cn.totime.data.entity.LetterType}
     *
     * @param id 主键
     * @return 对应数据
     */
    @RequestMapping("select/{id}")
    @ApiOperation(value = "查询单条数据", notes = "查询单条类型数据")
    @Cacheable(value = "letter-type", key = "#id")
    public ResultVO<LetterType> select(@PathVariable("id") Long id) {
        return new ResultVO<LetterType>().success("查询成功", letterTypeServiceImpl.getById(id));
    }

    /**
     * 通过主键删除单条数据
     *
     * @param id 主键
     * @return 对应数据
     */
    @RequestMapping("delete/{id}")
    @ApiOperation(value = "删除单条数据", notes = "删除一条信件类型")
    @CacheEvict(value = "letter-type", key = "#id")
    public ResultVO<Boolean> delete(@PathVariable("id") Long id) {
        return new ResultVO<Boolean>().success("删除成功", letterTypeServiceImpl.removeById(id));
    }

    /**
     * 新增数据{@link cn.totime.data.entity.LetterType}
     *
     * @param letterType 实例对象
     * @return 对应数据
     */
    @RequestMapping("insert")
    @ApiOperation(value = "新增数据", notes = "新增一条信件类型")
    @CachePut(value = "letter-type", key = "#letterType.getLetterTypeId()")
    public ResultVO<Boolean> insert(LetterType letterType) {
        return new ResultVO<Boolean>().success("新增成功", letterTypeServiceImpl.save(letterType));
    }
}