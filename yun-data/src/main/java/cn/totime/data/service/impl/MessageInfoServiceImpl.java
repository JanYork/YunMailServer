package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.MessageInfoMapper;
import cn.totime.data.entity.MessageInfo;
import cn.totime.data.service.MessageInfoService;
import org.springframework.stereotype.Service;

/**
 * (MessageInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:03
 */
@Service("messageInfoService")
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements MessageInfoService {

}