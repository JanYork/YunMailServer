package net.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.totime.mail.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件与信件评论表(Comment)表数据层
 *
 * @author JanYork
 * @since 2023-04-23 14:54:47
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
