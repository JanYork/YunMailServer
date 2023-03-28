package net.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.totime.mail.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据层
 *
 * @author JanYork
 * @since 2023-03-26 17:53:58
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}