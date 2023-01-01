package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.YunUserMapper;
import cn.totime.data.entity.YunUser;
import cn.totime.data.service.YunUserService;
import org.springframework.stereotype.Service;

/**
 * (YunUser)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:13
 */
@Service("yunUserService")
public class YunUserServiceImpl extends ServiceImpl<YunUserMapper, YunUser> implements YunUserService {

}