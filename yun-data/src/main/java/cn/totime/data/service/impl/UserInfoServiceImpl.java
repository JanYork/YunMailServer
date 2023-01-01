package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.UserInfoMapper;
import cn.totime.data.entity.UserInfo;
import cn.totime.data.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * (UserInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:08
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}