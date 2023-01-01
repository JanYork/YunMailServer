package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.LogisticsInfoMapper;
import cn.totime.data.entity.LogisticsInfo;
import cn.totime.data.service.LogisticsInfoService;
import org.springframework.stereotype.Service;

/**
 * (LogisticsInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:43:06
 */
@Service("logisticsInfoService")
public class LogisticsInfoServiceImpl extends ServiceImpl<LogisticsInfoMapper, LogisticsInfo> implements LogisticsInfoService {

}