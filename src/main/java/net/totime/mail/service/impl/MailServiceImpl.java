package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Mail;
import net.totime.mail.mapper.MailMapper;
import net.totime.mail.service.MailService;
import org.springframework.stereotype.Service;

/**
 * (Mail)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:03
 */
@Service
public class MailServiceImpl extends ServiceImpl<MailMapper, Mail> implements MailService {

}