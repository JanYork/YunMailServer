package net.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.totime.mail.entity.Oauth;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户第三方授权表(Oauth)表数据层
 *
 * @author JanYork
 * @since 2023-04-02 10:38:46
 */
@Mapper
public interface OauthMapper extends BaseMapper<Oauth> {

}