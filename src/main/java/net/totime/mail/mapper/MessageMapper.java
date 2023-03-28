package net.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.totime.mail.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信任务表(Message)表数据层
 *
 * @author JanYork
 * @since 2023-03-28 16:24:44
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}