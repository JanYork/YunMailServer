package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.MailInfoMapper;
import cn.totime.data.entity.MailInfo;
import cn.totime.data.service.MailInfoService;
import org.springframework.stereotype.Service;

/**
 * (MailInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:01
 */
@Service("mailInfoService")
public class MailInfoServiceImpl extends ServiceImpl<MailInfoMapper, MailInfo> implements MailInfoService {

}