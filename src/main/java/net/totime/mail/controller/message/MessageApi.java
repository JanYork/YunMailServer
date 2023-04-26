package net.totime.mail.controller.message;

import net.totime.mail.domain.message.MessageOperateService;
import net.totime.mail.dto.MessageDTO;
import net.totime.mail.entity.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 短信任务表(Message)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 短信服务接口
 * @since 2023-03-28 16:24:42
 */
@RestController
@RequestMapping("/api/message")
public class MessageApi {
    @Resource
    private MessageOperateService mos;

    /**
     * 新增短信任务
     * @param messageDTO 短信信息
     * @return {@link Boolean}
     */
    @PostMapping("/add")
    public Boolean addMessage(MessageDTO messageDTO) {
        return mos.addMessage(messageDTO);
    }

    /**
     * 删除短信任务
     * @param id 短信id
     * @return {@link Boolean}
     */
    @PostMapping("/delete")
    public Boolean deleteMessage(Long id) {
        return mos.deleteMessage(id);
    }

    /**
     * 更新短信任务
     * @param messageDTO 短信信息
     * @return {@link Boolean}
     */
    @PostMapping("/update")
    public Boolean updateMessage(MessageDTO messageDTO) {
        return mos.updateMessage(messageDTO);
    }

    /**
     * 获取短信任务
     * @param id 短信id
     * @return {@link Message}
     */
    @PostMapping("/get")
    public Message getMessage(Long id) {
        return mos.getMessage(id);
    }
}
