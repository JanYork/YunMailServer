package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Logistics;
import net.totime.mail.mapper.LogisticsMapper;
import net.totime.mail.service.LogisticsService;
import org.springframework.stereotype.Service;

/**
 * 信件物流表(Logistics)表服务实现类
 *
 * @author JanYork
 * @since 2023-05-12 15:58:40
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

}
