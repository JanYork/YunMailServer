package net.totime.mail;

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
public class MailApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
}