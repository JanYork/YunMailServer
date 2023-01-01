package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.WorkTicketsTypeMapper;
import cn.totime.data.entity.WorkTicketsType;
import cn.totime.data.service.WorkTicketsTypeService;
import org.springframework.stereotype.Service;

/**
 * (WorkTicketsType)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:12
 */
@Service("workTicketsTypeService")
public class WorkTicketsTypeServiceImpl extends ServiceImpl<WorkTicketsTypeMapper, WorkTicketsType> implements WorkTicketsTypeService {

}