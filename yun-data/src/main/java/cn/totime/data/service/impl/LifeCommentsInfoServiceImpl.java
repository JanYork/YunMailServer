package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.LifeCommentsInfoMapper;
import cn.totime.data.entity.LifeCommentsInfo;
import cn.totime.data.service.LifeCommentsInfoService;
import org.springframework.stereotype.Service;

/**
 * (LifeCommentsInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:41:58
 */
@Service("lifeCommentsInfoService")
public class LifeCommentsInfoServiceImpl extends ServiceImpl<LifeCommentsInfoMapper, LifeCommentsInfo> implements LifeCommentsInfoService {

}