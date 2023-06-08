/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.sms.aliyun;

import com.alibaba.cloud.spring.boot.sms.SmsUpMessageListener;
import com.aliyun.mns.model.Message;


/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 如果发送的短信需要接收对方回复的状态消息，只需实现该接口并初始化一个 Spring Bean 即可。
 * @see SmsUpMessageListener
 * @since 1.0.0
 */
public class SmsUpMessageListenerImpl implements SmsUpMessageListener {
    @Override
    public boolean dealMessage(Message message) {
        System.err.println(this.getClass().getName() + "; " + message.toString());
        return true;
    }
}
