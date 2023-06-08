/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.sms.aliyun;

import com.alibaba.cloud.spring.boot.sms.SmsReportMessageListener;
import com.aliyun.mns.model.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 如果需要监听短信是否被对方成功接收，只需实现这个接口并初始化一个 Spring Bean 即可。
 * @see SmsReportMessageListener
 * @since 1.0.0
 */
public class SmsReportMessageListenerImpl implements SmsReportMessageListener {
    private final List<Message> smsReportMessageSet = new LinkedList<>();

    @Override
    public boolean dealMessage(Message message) {
        smsReportMessageSet.add(message);
        System.err.println(this.getClass().getName() + "; " + message.toString());
        return true;
    }
    public List<Message> getSmsReportMessageSet() {
        return smsReportMessageSet;
    }
}
