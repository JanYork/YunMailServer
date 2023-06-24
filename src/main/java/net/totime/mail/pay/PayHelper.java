/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.pay;

import net.totime.mail.properties.AliPayProperties;
import net.totime.mail.properties.WeiXinPayProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/24
 * @description 支付辅助类
 * @since 1.0.0
 */
@Component
public class PayHelper {
    @Resource
    private WeiXinPayProperties weiXinPayProperties;
    @Resource
    private AliPayProperties aliPayProperties;
}
