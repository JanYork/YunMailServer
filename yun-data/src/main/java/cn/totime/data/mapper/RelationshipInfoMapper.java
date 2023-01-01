package cn.totime.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.totime.data.entity.RelationshipInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (RelationshipInfo)表数据库访问层
 *
 * @author JanYork
 * @since 2023-01-01 20:44:07
 */
@Mapper
public interface RelationshipInfoMapper extends BaseMapper<RelationshipInfo> {

}