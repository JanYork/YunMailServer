package com.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.totime.mail.entity.Mail;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Mail)表数据层
 *
 * @author JanYork
 * @since 2023-03-26 17:54:03
 */
@Mapper
public interface MailMapper extends BaseMapper<Mail> {

}