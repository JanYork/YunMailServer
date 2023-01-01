package cn.totime.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.totime.data.entity.MessageInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (MessageInfo)表数据库访问层
 *
 * @author JanYork
 * @since 2023-01-01 20:44:03
 */
@Mapper
public interface MessageInfoMapper extends BaseMapper<MessageInfo> {

}