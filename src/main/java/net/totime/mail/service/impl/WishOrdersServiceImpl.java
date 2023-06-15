/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.WishOrders;
import net.totime.mail.mapper.WishOrdersMapper;
import net.totime.mail.service.WishOrdersService;
import org.springframework.stereotype.Service;

/**
 * 信件订单表(WishOrders)表服务实现类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:39
 */
@Service
public class WishOrdersServiceImpl extends ServiceImpl<WishOrdersMapper, WishOrders> implements WishOrdersService {

}
