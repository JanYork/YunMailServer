/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.open;

import net.totime.mail.util.GenerateAvatar;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/17
 * @description 头像接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/avatar")
public class AvatarApi {
    @RequestMapping(value = "/getAvatar", produces = "image/png")
    public byte[] getAvatar(String str) throws IOException {
        return GenerateAvatar.generateAvatar((long) str.hashCode());
    }
}
