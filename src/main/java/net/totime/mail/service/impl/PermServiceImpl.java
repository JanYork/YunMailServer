package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Perm;
import net.totime.mail.mapper.PermMapper;
import net.totime.mail.service.PermService;
import org.springframework.stereotype.Service;

/**
 * (Perm)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:02
 */
@Service
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements PermService {

}