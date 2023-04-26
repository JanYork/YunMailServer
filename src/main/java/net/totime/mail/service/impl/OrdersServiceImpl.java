package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.Orders;
import net.totime.mail.mapper.OrdersMapper;
import net.totime.mail.service.OrdersService;
import org.springframework.stereotype.Service;

/**
 * 全局订单表(Orders)表服务实现类
 *
 * @author JanYork
 * @since 2023-04-14 20:28:38
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
