package com.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.totime.mail.entity.Letter;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Letter)表数据层
 *
 * @author JanYork
 * @since 2023-03-26 17:54:04
 */
@Mapper
public interface LetterMapper extends BaseMapper<Letter> {

}