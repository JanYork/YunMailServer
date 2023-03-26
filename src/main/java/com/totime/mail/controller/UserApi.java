package com.totime.mail.controller;

import com.totime.mail.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (User)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-03-26 17:53:57
 */
@RestController
@RequestMapping("/user")
public class UserApi {
    @RequestMapping("/insert")
    public String insert() {
        User user = new User();
        //
        return "写入成功";
    }
}