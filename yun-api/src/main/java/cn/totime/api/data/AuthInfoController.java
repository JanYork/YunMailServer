package cn.totime.api.data;

import cn.totime.common.vo.ResultVO;
import cn.totime.data.entity.AuthInfo;
import cn.totime.data.service.impl.AuthInfoServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (AuthInfo)表控制层
 *
 * @author JanYork
 * @since 2023-01-02 13:15:26
 */
@RestController
@RequestMapping("auth")
@Api(tags = "第三方登录授权信息")
@ApiSupport(author = "JanYork")
public class AuthInfoController {
    @Autowired
    private AuthInfoServiceImpl authInfoServiceImpl;

    /**
     * 查询所有数据{@link cn.totime.data.entity.AuthInfo}
     *
     * @return 对应数据集合
     */
    @Deprecated
    @RequestMapping("select/all")
    @ApiOperation(value = "查询所有数据", notes = "查询第三方授权信息所有数据")
    @Cacheable(value = "all-auth-info", key = "#root.methodName")
    public ResultVO<List<AuthInfo>> selectAll() {
        return new ResultVO<List<AuthInfo>>().success("查询成功", authInfoServiceImpl.list());
    }

    /**
     * 通过主键查询单条数据{@link cn.totime.data.entity.AuthInfo}
     *
     * @param id 主键
     * @return 对应数据
     */
    @RequestMapping("select/{id}")
    @ApiOperation(value = "查询单条数据", notes = "通过主键查询第三方授权信息单条数据")
    @Cacheable(value = "auth-info", key = "#id")
    public ResultVO<AuthInfo> select(@PathVariable("id") Long id) {
        return new ResultVO<AuthInfo>().success("查询成功", authInfoServiceImpl.getById(id));
    }

    /**
     * 通过用户ID查询用户所有授权数据{@link cn.totime.data.entity.AuthInfo}
     *
     * @param uid 用户ID
     * @return 对应数据
     */
    @RequestMapping("select/user/{uid}")
    @ApiOperation(value = "查询用户授权数据", notes = "通过用户ID查询第三方授权信息所有数据")
    @Cacheable(value = "all-auth-info-by-uid", key = "#uid")
    public ResultVO<List<AuthInfo>> selectAllByUserId(@PathVariable("uid") Long uid) {
        return new ResultVO<List<AuthInfo>>()
                .success("查询成功", authInfoServiceImpl.list(
                                new QueryWrapper<AuthInfo>().eq("u_id", uid)
                        )
                );
    }

    /**
     * 通过用户ID查询用户单条授权数据{@link cn.totime.data.entity.AuthInfo}
     *
     * @param uid  用户ID
     * @param type 授权类型
     * @return 对应数据
     */
    @RequestMapping("select/user/{uid}/{type}")
    @ApiOperation(value = "查询用户授权数据", notes = "通过用户ID查询第三方授权信息所有数据")
    @Cacheable(value = "auth-info-by-uid_type", key = "#uid")
    public ResultVO<AuthInfo> selectSingleByUserId(@PathVariable("uid") Long uid, @PathVariable("type") String type) {
        return new ResultVO<AuthInfo>()
                .success("查询成功", authInfoServiceImpl.getOne(
                                new QueryWrapper<AuthInfo>()
                                        .eq("u_id", uid)
                                        .eq("auth_type", type)
                        )
                );
    }

    /**
     * 新增数据{@link cn.totime.data.entity.AuthInfo}
     *
     * @param authInfo 实体对象
     * @return 是否成功
     */
    @PostMapping("add")
    @ApiOperation(value = "新增数据", notes = "新增第三方授权信息数据")
    @CachePut(value = "auth-info", key = "#authInfo.getUId()")
    public ResultVO<Boolean> add(AuthInfo authInfo) {
        return new ResultVO<Boolean>().success("新增成功", authInfoServiceImpl.save(authInfo));
    }

    /**
     * 修改数据{@link cn.totime.data.entity.AuthInfo}
     *
     * @param authInfo 实体对象
     * @return 是否成功
     */
    @PostMapping("update")
    @ApiOperation(value = "修改数据", notes = "修改第三方授权信息数据")
    @CachePut(value = "auth-info", key = "#authInfo.getUId()")
    public ResultVO<Boolean> update(AuthInfo authInfo) {
        return new ResultVO<Boolean>().success("修改成功", authInfoServiceImpl.updateById(authInfo));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @PostMapping("delete/{id}")
    @ApiOperation(value = "删除单个数据", notes = "删除第三方授权信息数据")
    @CacheEvict(value = "auth-info", key = "#id")
    public ResultVO<Boolean> delete(@PathVariable("id") Long id) {
        return new ResultVO<Boolean>().success("删除成功", authInfoServiceImpl.removeById(id));
    }

    /**
     * 删除用户所有授权数据
     *
     * @param uid 用户ID
     * @return 是否成功
     */
    @Deprecated
    @PostMapping("delete/user/{uid}")
    @ApiOperation(value = "删除用户所有授权数据", notes = "根据用户ID删除用户所有授权数据")
    @CacheEvict(value = "all-auth-info-by-uid", key = "#uid")
    public ResultVO<Boolean> deleteAllByUserId(@PathVariable("uid") Long uid) {
        return new ResultVO<Boolean>().success("删除成功", authInfoServiceImpl.remove(
                        new QueryWrapper<AuthInfo>().eq("u_id", uid)
                )
        );
    }

    /**
     * 删除用户单条授权数据
     *
     * @param uid  用户ID
     * @param type 授权类型
     * @return 是否成功
     */
    @PostMapping("delete/user/{uid}/{type}")
    @ApiOperation(value = "删除用户单条授权数据", notes = "根据用户ID和授权类型删除用户单条授权数据")
    @CacheEvict(value = "auth-info-by-uid_type", key = "#uid")
    public ResultVO<Boolean> deleteSingleByUserId(@PathVariable("uid") Long uid, @PathVariable("type") String type) {
        return new ResultVO<Boolean>().success("删除成功", authInfoServiceImpl.remove(
                        new QueryWrapper<AuthInfo>()
                                .eq("u_id", uid)
                                .eq("auth_type", type)
                )
        );
    }
}