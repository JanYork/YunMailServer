package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.WishInfoMapper;
import cn.totime.data.entity.WishInfo;
import cn.totime.data.service.WishInfoService;
import org.springframework.stereotype.Service;

/**
 * (WishInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:11
 */
@Service("wishInfoService")
public class WishInfoServiceImpl extends ServiceImpl<WishInfoMapper, WishInfo> implements WishInfoService {

}