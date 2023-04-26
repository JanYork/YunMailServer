package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Comment;
import net.totime.mail.mapper.CommentMapper;
import net.totime.mail.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * 邮件与信件评论表(Comment)表服务实现类
 *
 * @author JanYork
 * @since 2023-04-23 14:54:46
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
