package cn.totime.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.totime.data.entity.LetterType;
import org.apache.ibatis.annotations.Mapper;

/**
 * (LetterType)表数据库访问层
 *
 * @author JanYork
 * @since 2023-01-01 20:40:47
 */
@Mapper
public interface LetterTypeMapper extends BaseMapper<LetterType> {

}