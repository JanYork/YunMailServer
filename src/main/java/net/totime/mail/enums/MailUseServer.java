package net.totime.mail.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 邮件发送使用的服务
 * @see Enum
 * @since 1.0.0
 */
public enum MailUseServer {
    /**
     * 腾讯云
     */
    TENCENT(1, "腾讯云"),
    /**
     * 云寄
     */
    YUN(0, "云寄");
    @EnumValue
    private Integer id;
    @JsonValue
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    MailUseServer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}