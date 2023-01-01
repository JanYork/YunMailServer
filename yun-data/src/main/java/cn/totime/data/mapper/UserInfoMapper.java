package cn.totime.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.totime.data.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (UserInfo)表数据库访问层
 *
 * @author JanYork
 * @since 2023-01-01 20:44:09
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}