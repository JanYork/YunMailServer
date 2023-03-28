package net.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.totime.mail.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Admin)表数据层
 *
 * @author JanYork
 * @since 2023-03-26 17:54:04
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}