package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.RoleInfoMapper;
import cn.totime.data.entity.RoleInfo;
import cn.totime.data.service.RoleInfoService;
import org.springframework.stereotype.Service;

/**
 * (RoleInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:07
 */
@Service("roleInfoService")
public class RoleInfoServiceImpl extends ServiceImpl<RoleInfoMapper, RoleInfo> implements RoleInfoService {

}