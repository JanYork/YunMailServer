/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

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
