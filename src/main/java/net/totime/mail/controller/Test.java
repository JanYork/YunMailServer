/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller;

import com.alibaba.fastjson2.JSON;
import com.baidu.aip.contentcensor.AipContentCensor;
import net.totime.mail.pojo.BaiDuAiBack;
import net.totime.mail.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/18
 * @description TEST
 * @since 1.0.0
 */
@RestController
@RequestMapping
public class Test {
    @Resource
    private AipContentCensor ai;

    @GetMapping("/test")
    public ApiResponse<String> test(String text) {
        BaiDuAiBack baiDuAiBack = JSON.parseObject(ai.textCensorUserDefined(text).toString(), BaiDuAiBack.class);
        boolean compliance = baiDuAiBack.isCompliance();
        return compliance ? ApiResponse.ok("合规") : ApiResponse.fail("内容不合规").message(baiDuAiBack.getData().get(0).getMsg());
    }
}
