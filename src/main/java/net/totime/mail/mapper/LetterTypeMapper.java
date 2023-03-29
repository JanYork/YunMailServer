package net.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.totime.mail.entity.LetterType;
import org.apache.ibatis.annotations.Mapper;

/**
 * 信件类型表(LetterType)表数据层
 *
 * @author JanYork
 * @since 2023-03-29 10:46:48
 */
@Mapper
public interface LetterTypeMapper extends BaseMapper<LetterType> {

}