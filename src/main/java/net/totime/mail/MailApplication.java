/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package net.totime.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/26
 * @description 云寄2.0.0服务端
 * @see <a href="https://totime.net">云寄</a>
 * @since 1.0.0
 */
@SpringBootApplication
public class MailApplication {
    public static void main(String[] args) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(MailApplication.class, args);
    }
}
