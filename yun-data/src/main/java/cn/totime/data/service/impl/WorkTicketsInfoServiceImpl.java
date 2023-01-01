package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.WorkTicketsInfoMapper;
import cn.totime.data.entity.WorkTicketsInfo;
import cn.totime.data.service.WorkTicketsInfoService;
import org.springframework.stereotype.Service;

/**
 * (WorkTicketsInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:12
 */
@Service("workTicketsInfoService")
public class WorkTicketsInfoServiceImpl extends ServiceImpl<WorkTicketsInfoMapper, WorkTicketsInfo> implements WorkTicketsInfoService {

}