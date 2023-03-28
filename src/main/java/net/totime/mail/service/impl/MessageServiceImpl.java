package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Message;
import net.totime.mail.mapper.MessageMapper;
import net.totime.mail.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * 短信任务表(Message)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-28 16:24:44
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}