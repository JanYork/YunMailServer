/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller;

import net.totime.mail.service.OauthService;
import net.totime.mail.util.RedisUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Test {
    @Resource
    private OauthService oauthService;
    @Resource
    private RedisUtil rut;

    @GetMapping("/test")
    public void test() {
        String key = "wx_loginoGthh6bY41yDGTMH4gm8O6ZmN5Q8";
        String id = "oGthh6bY41yDGTMH4gm8O6ZmN5Q8";
        rut.set("wx_6M3K9J", id);
        rut.set(key, 1);
    }
}
