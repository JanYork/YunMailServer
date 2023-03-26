package com.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.totime.mail.entity.StateType;
import com.totime.mail.mapper.StateTypeMapper;
import com.totime.mail.service.StateTypeService;
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