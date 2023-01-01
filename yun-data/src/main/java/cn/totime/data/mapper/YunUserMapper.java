package cn.totime.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.totime.data.entity.YunUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * (YunUser)表数据库访问层
 *
 * @author JanYork
 * @since 2023-01-01 20:44:13
 */
@Mapper
public interface YunUserMapper extends BaseMapper<YunUser> {

}