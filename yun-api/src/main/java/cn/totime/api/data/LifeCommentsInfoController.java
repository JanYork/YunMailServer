package cn.totime.api.data;

import cn.totime.common.vo.ResultVO;
import cn.totime.data.entity.LifeCommentsInfo;
import cn.totime.data.service.impl.LifeCommentsInfoServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (LifeCommentsInfo)表控制层
 *
 * @author JanYork
 * @since 2023-01-02 13:15:28
 */
@RestController
@RequestMapping("lifeComments")
@Api(tags = "生活社区评论接口")
@ApiSupport(author = "JanYork")
public class LifeCommentsInfoController {
    @Autowired
    private LifeCommentsInfoServiceImpl lifeCommentsInfoServiceImpl;

    /**
     * 通过主键查询单条数据{@link cn.totime.data.entity.LifeCommentsInfo}
     *
     * @param id 主键
     * @return 对应数据
     */
    @RequestMapping("select/{id}")
    @ApiOperation(value = "查询单条数据", notes = "通过主键查询生活社区评论单条数据")
    @Cacheable(value = "life-comments-info", key = "#id")
    public ResultVO<LifeCommentsInfo> select(@PathVariable("id") Long id) {
        return new ResultVO<LifeCommentsInfo>().success("查询成功", lifeCommentsInfoServiceImpl.getById(id));
    }

    /**
     * 查询所有数据{@link cn.totime.data.entity.LifeCommentsInfo}
     *
     * @return 对应数据
     */
    @Deprecated
    @RequestMapping("select/all")
    @ApiOperation(value = "查询所有数据", notes = "查询所有生活社区评论数据")
    @Cacheable(value = "life-comments-info", key = "#root.methodName")
    public ResultVO<List<LifeCommentsInfo>> selectAll() {
        return new ResultVO<List<LifeCommentsInfo>>().success("查询成功", lifeCommentsInfoServiceImpl.list());
    }

    /**
     * 查询用户所有评论{@link cn.totime.data.entity.LifeCommentsInfo}
     *
     * @param userId 用户id
     * @return 对应数据
     */
    @Deprecated
    @RequestMapping("select/user/{uid}")
    @ApiOperation(value = "查询用户评论", notes = "查询用户评论")
    @Cacheable(value = "life-comments-info", key = "#root.methodName")
    public ResultVO<List<LifeCommentsInfo>> selectUserComments(@PathVariable("uid") Long userId) {
        return new ResultVO<List<LifeCommentsInfo>>().success("查询成功", lifeCommentsInfoServiceImpl.list(
                new QueryWrapper<LifeCommentsInfo>().eq("comments_u_id", userId)
        ));
    }

    /**
     * 查询用户所在文章的所有评论{@link cn.totime.data.entity.LifeCommentsInfo}
     *
     * @param uid 用户id
     * @param lid 文章id
     * @return 对应数据
     */
    @Deprecated
    @RequestMapping("select/user/{uid}/{lid}")
    @ApiOperation(value = "查询文章指定用户评论", notes = "查询指定文章用户的所有评论")
    @Cacheable(value = "life-comments-info-uid_lid", key = "#root.methodName")
    public ResultVO<List<LifeCommentsInfo>> selectUserLifeComments(@PathVariable("uid") Long uid, @PathVariable("lid") Long lid) {
        return new ResultVO<List<LifeCommentsInfo>>().success("查询成功", lifeCommentsInfoServiceImpl.list(
                new QueryWrapper<LifeCommentsInfo>()
                        .eq("comments_u_id", uid)
                        .eq("comments_l_id", lid)
        ));
    }

    /**
     * 查询文章的所有评论{@link cn.totime.data.entity.LifeCommentsInfo}
     *
     * @param lid 文章id
     * @return 对应数据
     */
    @Deprecated
    @RequestMapping("select/life/{lid}")
    @ApiOperation(value = "查询文章的评论", notes = "查询文章的所有评论")
    @Cacheable(value = "life-comments-info-lid", key = "#root.methodName")
    public ResultVO<List<LifeCommentsInfo>> selectLifeComments(@PathVariable("lid") Long lid) {
        return new ResultVO<List<LifeCommentsInfo>>().success("查询成功", lifeCommentsInfoServiceImpl.list(
                new QueryWrapper<LifeCommentsInfo>().eq("comments_l_id", lid)
        ));
    }
}