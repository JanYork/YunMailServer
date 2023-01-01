package cn.totime.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.totime.data.entity.MailInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (MailInfo)表数据库访问层
 *
 * @author JanYork
 * @since 2023-01-01 20:44:01
 */
@Mapper
public interface MailInfoMapper extends BaseMapper<MailInfo> {

}