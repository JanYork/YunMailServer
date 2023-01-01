package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.WishCommentsInfoMapper;
import cn.totime.data.entity.WishCommentsInfo;
import cn.totime.data.service.WishCommentsInfoService;
import org.springframework.stereotype.Service;

/**
 * (WishCommentsInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:09
 */
@Service("wishCommentsInfoService")
public class WishCommentsInfoServiceImpl extends ServiceImpl<WishCommentsInfoMapper, WishCommentsInfo> implements WishCommentsInfoService {

}