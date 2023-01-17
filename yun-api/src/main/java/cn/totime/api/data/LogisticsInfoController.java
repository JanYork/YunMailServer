package cn.totime.api.data;

import cn.totime.common.vo.ResultVO;
import cn.totime.data.entity.LogisticsInfo;
import cn.totime.data.service.impl.LogisticsInfoServiceImpl;
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
 * (LogisticsInfo)表控制层
 *
 * @author JanYork
 * @since 2023-01-02 13:15:29
 */
@RestController
@RequestMapping("logistics")
@Api(tags = "物流信息接口")
@ApiSupport(author = "JanYork")
public class LogisticsInfoController {
    @Autowired
    private LogisticsInfoServiceImpl logisticsInfoServiceImpl;

    /**
     * 通过主键查询单条数据{@link cn.totime.data.entity.LogisticsInfo}
     *
     * @param id 主键
     * @return 对应数据
     */
    @RequestMapping("select/{id}")
    @ApiOperation(value = "查询单条数据", notes = "通过主键查询物流信息单条数据")
    @Cacheable(value = "logistics-info", key = "#id")
    public ResultVO<LogisticsInfo> select(@PathVariable("id") Long id) {
        return new ResultVO<LogisticsInfo>().success("查询成功", logisticsInfoServiceImpl.getById(id));
    }

    /**
     * 查询所有数据{@link cn.totime.data.entity.LogisticsInfo}
     *
     * @return 对应数据
     */
    @RequestMapping("select/all")
    @ApiOperation(value = "查询所有数据", notes = "查询所有物流信息数据")
    @Cacheable(value = "logistics-info", key = "#root.methodName")
    public ResultVO<List<LogisticsInfo>> selectAll() {
        return new ResultVO<List<LogisticsInfo>>().success("查询成功", logisticsInfoServiceImpl.list());
    }

    /**
     * 查询指定信件物流数据{@link cn.totime.data.entity.LogisticsInfo}
     *
     * @param lid 信件id
     * @return 对应数据
     */
    @RequestMapping("select/{lid}")
    @ApiOperation(value = "查询用户所有物流数据", notes = "查询用户所有物流数据")
    @Cacheable(value = "logistics-info-lid", key = "#lid")
    public ResultVO<LogisticsInfo> selectAllByUid(@PathVariable("lid") Long lid) {
        return new ResultVO<LogisticsInfo>().success("查询成功", logisticsInfoServiceImpl.getOne(
                new QueryWrapper<LogisticsInfo>().eq("letter_id", lid)
        ));
    }

    /**
     * 查询指定ID(List)信件物流数据{@link cn.totime.data.entity.LogisticsInfo}
     *
     * @param lidList 信件id(List)
     * @return 对应数据
     */
    @Deprecated
    @RequestMapping("select/lid/list")
    @ApiOperation(value = "查询指定ID列表物流数据", notes = "根据信件ID列表查询物流数据")
    @Cacheable(value = "logistics-info-lid-list", key = "'list'")
    public ResultVO<List<LogisticsInfo>> selectAllByUidList(List<Long> lidList) {
        return new ResultVO<List<LogisticsInfo>>().success("查询成功", logisticsInfoServiceImpl.list(
                new QueryWrapper<LogisticsInfo>().in("letter_id", lidList)
        ));
    }

    /**
     * 删除数据
     *
     * @param lid 信件id
     * @return 删除结果
     */
    @RequestMapping("delete/{lid}")
    @ApiOperation(value = "删除指定ID信件物流数据", notes = "根据信件ID删除物流数据")
    @CacheEvict(value = "logistics-info-lid", key = "#lid")
    public ResultVO<Boolean> deleteByLid(@PathVariable("lid") Long lid) {
        return new ResultVO<Boolean>().success("删除成功", logisticsInfoServiceImpl.remove(
                new QueryWrapper<LogisticsInfo>().eq("letter_id", lid)
        ));
    }

    /**
     * 删除数据
     *
     * @param lidList 信件id(List)
     * @return 删除结果
     */
    @Deprecated
    @RequestMapping("delete/lid/list")
    @ApiOperation(value = "删除指定ID列表信件物流数据", notes = "根据信件ID列表删除物流数据")
    @CacheEvict(value = "logistics-info-lid-list", key = "'list'")
    public ResultVO<Boolean> deleteByLidList(List<Long> lidList) {
        return new ResultVO<Boolean>().success("删除成功", logisticsInfoServiceImpl.remove(
                new QueryWrapper<LogisticsInfo>().in("letter_id", lidList)
        ));
    }

    /**
     * 新增数据
     *
     * @param logisticsInfo 实例对象
     * @return 新增结果
     */
    @RequestMapping("insert")
    @ApiOperation(value = "新增物流信息", notes = "新增物流信息")
    @CachePut(value = "logistics-info", key = "#logisticsInfo.getLogisticsId()")
    public ResultVO<Boolean> insert(LogisticsInfo logisticsInfo) {
        return new ResultVO<Boolean>().success("新增成功", logisticsInfoServiceImpl.save(logisticsInfo));
    }

    /**
     * 修改数据
     *
     * @param logisticsInfo 实例对象
     * @return 修改结果
     */
    @RequestMapping("update")
    @ApiOperation(value = "修改物流信息", notes = "修改物流信息")
    @CachePut(value = "logistics-info", key = "#logisticsInfo.getLogisticsId()")
    public ResultVO<Boolean> update(LogisticsInfo logisticsInfo) {
        return new ResultVO<Boolean>().success("修改成功", logisticsInfoServiceImpl.updateById(logisticsInfo));
    }
}