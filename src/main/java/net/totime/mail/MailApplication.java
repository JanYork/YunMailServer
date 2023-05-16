/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * 此版权个人开发者保留最终归属权与解释权，非版权所有者授权禁止商用与演绎.
 */
package net.totime.mail;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/26
 * @description 云寄2.0.0服务端
 * @see <a href="https://totime.net">云寄</a>
 * @since 1.0.0
 */
@SpringBootApplication
@EnableMethodCache(basePackages = "net.totime.mail")
public class MailApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
}
