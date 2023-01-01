package cn.totime.data.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.totime.data.mapper.LetterTypeMapper;
import cn.totime.data.entity.LetterType;
import cn.totime.data.service.LetterTypeService;
import org.springframework.stereotype.Service;

/**
 * (LetterType)表服务实现类
 *
 * @author JanYork
 * @since 2023-01-01 20:40:46
 */
@Service("letterTypeService")
public class LetterTypeServiceImpl extends ServiceImpl<LetterTypeMapper, LetterType> implements LetterTypeService {

}