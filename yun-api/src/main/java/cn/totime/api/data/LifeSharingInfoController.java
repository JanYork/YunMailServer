package cn.totime.api.data;

import cn.totime.common.vo.ResultVO;
import cn.totime.data.entity.LifeSharingInfo;
import cn.totime.data.service.impl.LifeSharingInfoServiceImpl;
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
 * (LifeSharingInfo)表控制层
 *
 * @author JanYork
 * @since 2023-01-02 13:15:29
 */
@RestController
@RequestMapping("lifeSharing")
@Api(tags = "生活社区分享接口")
@ApiSupport(author = "JanYork")
public class LifeSharingInfoController {
    @Autowired
    private LifeSharingInfoServiceImpl lifeSharingInfoServiceImpl;

    /**
     * 通过主键查询单条数据{@link cn.totime.data.entity.LifeSharingInfo}
     *
     * @param id 主键
     * @return 对应数据
     */
    @RequestMapping("select/{id}")
    @ApiOperation(value = "查询单条数据", notes = "通过主键查询生活社区分享单条数据")
    @Cacheable(value = "life-sharing-info", key = "#id")
    public ResultVO<LifeSharingInfo> select(@PathVariable("id") Long id) {
        return new ResultVO<LifeSharingInfo>().success("查询成功", lifeSharingInfoServiceImpl.getById(id));
    }

    /**
     * 通过用户查询所有数据{@link cn.totime.data.entity.LifeSharingInfo}
     *
     * @param uid 用户id
     * @return 对应数据
     */
    @Deprecated
    @RequestMapping("select/{uid}")
    @ApiOperation(value = "查询所有数据", notes = "通过用户id查询生活社区分享所有数据")
    @Cacheable(value = "life-sharing-info-uid", key = "#uid")
    public ResultVO<List<LifeSharingInfo>> selectAll(@PathVariable("uid") Long uid) {
        return new ResultVO<List<LifeSharingInfo>>().success("查询成功", lifeSharingInfoServiceImpl.list(
                new QueryWrapper<LifeSharingInfo>().eq("u_id", uid)
        ));
    }

    /**
     * 删除单个数据
     *
     * @param id 主键
     * @return 对应数据
     */
    @RequestMapping("delete/{id}")
    @ApiOperation(value = "删除单条数据", notes = "通过主键删除生活社区分享数据")
    @CacheEvict(value = "life-sharing-info", key = "#id")
    public ResultVO<Boolean> delete(@PathVariable("id") Long id) {
        return new ResultVO<Boolean>().success("删除成功", lifeSharingInfoServiceImpl.removeById(id));
    }

    /**
     * 删除用户发文数据
     *
     * @param uid 用户id
     * @return 对应数据
     */
    @Deprecated
    @RequestMapping("delete/{uid}")
    @ApiOperation(value = "删除用户数据", notes = "通过用户id删除生活社区分享数据")
    @CacheEvict(value = "life-sharing-info-uid", key = "#uid")
    public ResultVO<Boolean> deleteAll(@PathVariable("uid") Long uid) {
        return new ResultVO<Boolean>().success("删除成功", lifeSharingInfoServiceImpl.remove(
                new QueryWrapper<LifeSharingInfo>().eq("u_id", uid)
        ));
    }

    /**
     * 新增数据{@link cn.totime.data.entity.LifeSharingInfo}
     *
     * @param lifeSharingInfo 实例对象
     * @return 对应数据
     */
    @RequestMapping("insert")
    @ApiOperation(value = "新增数据", notes = "新增生活社区分享数据")
    @CachePut(value = "life-sharing-info", key = "#lifeSharingInfo.getUId()")
    public ResultVO<Boolean> insert(LifeSharingInfo lifeSharingInfo) {
        return new ResultVO<Boolean>().success("新增成功", lifeSharingInfoServiceImpl.save(lifeSharingInfo));
    }

    /**
     * 修改数据{@link cn.totime.data.entity.LifeSharingInfo}
     *
     * @param lifeSharingInfo 实例对象
     * @return 对应数据
     */
    @RequestMapping("update")
    @ApiOperation(value = "修改数据", notes = "修改生活社区分享数据")
    @CachePut(value = "life-sharing-info", key = "#lifeSharingInfo.getUId()")
    public ResultVO<Boolean> update(LifeSharingInfo lifeSharingInfo) {
        return new ResultVO<Boolean>().success("修改成功", lifeSharingInfoServiceImpl.updateById(lifeSharingInfo));
    }
}