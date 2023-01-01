package cn.totime.data.service.impl;

import cn.totime.data.mapper.LetterInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.entity.LetterInfo;
import cn.totime.data.service.LetterInfoService;
import org.springframework.stereotype.Service;

/**
 * (LetterInfo)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:37:18
 */
@Service("letterInfoService")
public class LetterInfoServiceImpl extends ServiceImpl<LetterInfoMapper, LetterInfo> implements LetterInfoService {

}