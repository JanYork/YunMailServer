package cn.totime.data.service.impl;

import cn.totime.data.mapper.AuthInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.entity.AuthInfo;
import cn.totime.data.service.AuthInfoService;
import org.springframework.stereotype.Service;

/**
 * (AuthInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:25:59
 */
@Service("authInfoService")
public class AuthInfoServiceImpl extends ServiceImpl<AuthInfoMapper, AuthInfo> implements AuthInfoService {

}