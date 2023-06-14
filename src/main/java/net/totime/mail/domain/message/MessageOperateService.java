/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain.message;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import net.totime.mail.dto.MessageDTO;
import net.totime.mail.entity.back.Message;
import net.totime.mail.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/25
 * @description 短信服务
 * @since 1.0.0
 */
@Service
public class MessageOperateService {
    @Resource
    private MessageService messageService;

    /**
     * 新增短信任务
     * @param messageDTO 短信信息
     * @return {@link Boolean}
     */
    public Boolean addMessage(MessageDTO messageDTO) {
        Message message = new Message();
        BeanUtil.copyProperties(messageDTO, message);
        message.setCreateTime(new Date());
        message.setUserId(StpUtil.getLoginIdAsLong());
        //TODO: 2021/4/25 未完成 状态枚举
        message.setState(0);
        return messageService.save(message);
    }

    /**
     * 删除短信任务
     * @param id 短信id
     * @return {@link Boolean}
     */
    public Boolean deleteMessage(Long id) {
        return messageService.removeById(id);
    }

    /**
     * 更新短信任务
     * @param messageDTO 短信信息
     * @return {@link Boolean}
     */
    public Boolean updateMessage(MessageDTO messageDTO) {
        Message message = new Message();
        BeanUtil.copyProperties(messageDTO, message);
        return messageService.updateById(message);
    }

    /**
     * 获取短信任务
     * @param id 短信id
     * @return {@link Message}
     */
    public Message getMessage(Long id) {
        return messageService.getById(id);
    }
}
