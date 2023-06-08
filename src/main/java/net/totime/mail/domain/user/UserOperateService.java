/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain.user;

import net.totime.mail.entity.User;
import net.totime.mail.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/01
 * @description 邮件操作服务
 * @since 1.0.0
 */
@Service
public class UserOperateService {
    @Resource
    private UserService userService;

    /**
     * 判断用户是否存在
     *
     * @param id 用户ID
     * @return 是否存在
     */
    public Boolean isUserExist(String id) {
        User user = userService.getById(id);
        return user != null && StringUtils.isNotBlank(user.getPhone());
    }
}
