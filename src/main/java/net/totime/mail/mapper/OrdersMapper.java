package net.totime.mail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.totime.mail.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * 全局订单表(Orders)表数据层
 *
 * @author JanYork
 * @since 2023-04-14 20:28:38
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}
