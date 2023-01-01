package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.PermissionsInfoMapper;
import cn.totime.data.entity.PermissionsInfo;
import cn.totime.data.service.PermissionsInfoService;
import org.springframework.stereotype.Service;

/**
 * (PermissionsInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:04
 */
@Service("permissionsInfoService")
public class PermissionsInfoServiceImpl extends ServiceImpl<PermissionsInfoMapper, PermissionsInfo> implements PermissionsInfoService {

}