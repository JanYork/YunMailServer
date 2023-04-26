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
