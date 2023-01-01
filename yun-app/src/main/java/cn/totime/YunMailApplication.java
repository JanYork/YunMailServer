package cn.totime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

/**
 * <p>Copyright © JanYork</p>
 *
 * @author JanYork
 * @version 2.0
 * @date 2022年12月23日12点57分
 * @description 云寄服务端启动类
 * @since 1.0
 */
@SpringBootApplication
public class YunMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(YunMailApplication.class, args);
        System.out.println("云寄时光机启动成功啦!\n" +
                " __      __\n" +
                "( _\\    /_ )\n" +
                " \\ _\\  /_ / \n" +
                "  \\ _\\/_ /_ _\n" +
                "  |_____/_/ /|\n" +
                "  (  (_)__)J-)\n" +
                "  (  /`.,   /\n" +
                "   \\/  ;   /\n" +
                "    | === | 好耶！");
        //输出启动信息
        System.out.println("启动时间：" + new Date());
        System.out.println("开发者：JanYork");
        System.out.println("版本号：V2.0");
        System.out.println("请访问：http://localhost:8088");
        System.out.println("看文档：http://localhost:8088/swagger-ui/index.html");

    }
}