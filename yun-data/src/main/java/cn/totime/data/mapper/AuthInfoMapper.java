package cn.totime.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.totime.data.entity.AuthInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (AuthInfo)表数据库访问层
 *
 * @author JanYork
 * @since 2023-01-01 20:25:59
 */
@Mapper
public interface AuthInfoMapper extends BaseMapper<AuthInfo> {

}