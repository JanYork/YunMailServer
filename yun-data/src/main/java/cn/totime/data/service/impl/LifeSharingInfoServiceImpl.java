package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.LifeSharingInfoMapper;
import cn.totime.data.entity.LifeSharingInfo;
import cn.totime.data.service.LifeSharingInfoService;
import org.springframework.stereotype.Service;

/**
 * (LifeSharingInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:42:33
 */
@Service("lifeSharingInfoService")
public class LifeSharingInfoServiceImpl extends ServiceImpl<LifeSharingInfoMapper, LifeSharingInfo> implements LifeSharingInfoService {

}