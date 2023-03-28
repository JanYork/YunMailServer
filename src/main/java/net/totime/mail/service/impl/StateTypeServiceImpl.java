package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.StateType;
import net.totime.mail.mapper.StateTypeMapper;
import net.totime.mail.service.StateTypeService;
import org.springframework.stereotype.Service;

/**
 * (StateType)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 17:53:59
 */
@Service
public class StateTypeServiceImpl extends ServiceImpl<StateTypeMapper, StateType> implements StateTypeService {

}