package com.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.totime.mail.entity.Letter;
import com.totime.mail.mapper.LetterMapper;
import com.totime.mail.service.LetterService;
import org.springframework.stereotype.Service;

/**
 * (Letter)表服务实现类
 *
 * @author JanYork
 * @since 2023-03-26 17:54:04
 */
@Service
public class LetterServiceImpl extends ServiceImpl<LetterMapper, Letter> implements LetterService {

}