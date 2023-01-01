package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.RealNameInfoMapper;
import cn.totime.data.entity.RealNameInfo;
import cn.totime.data.service.RealNameInfoService;
import org.springframework.stereotype.Service;

/**
 * (RealNameInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:06
 */
@Service("realNameInfoService")
public class RealNameInfoServiceImpl extends ServiceImpl<RealNameInfoMapper, RealNameInfo> implements RealNameInfoService {

}