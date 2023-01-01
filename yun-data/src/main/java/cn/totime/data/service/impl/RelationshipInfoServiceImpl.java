package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.RelationshipInfoMapper;
import cn.totime.data.entity.RelationshipInfo;
import cn.totime.data.service.RelationshipInfoService;
import org.springframework.stereotype.Service;

/**
 * (RelationshipInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:44:07
 */
@Service("relationshipInfoService")
public class RelationshipInfoServiceImpl extends ServiceImpl<RelationshipInfoMapper, RelationshipInfo> implements RelationshipInfoService {

}