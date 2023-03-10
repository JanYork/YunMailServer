package cn.totime;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Date;

/**
 * <p>Copyright © JanYork</p>
 * <p>Company: 初柒网络科技</p>
 * <p><i>@RetrofitScan</i>注解用于标识Retrofit请求方法的文件包路径</p>
 *
 * @author JanYork
 * @version 2.0
 * @date 2022年12月23日12点57分
 * @description 云寄服务端启动类
 * @since 1.0
 */
@SpringBootApplication
@RetrofitScan(basePackages = "cn.totime.common.retrofit")
@EnableCaching
public class YunMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(YunMailApplication.class, args);
        String port = "8088";
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
        System.out.println("启动时间：" + String.format("%tF-%tT", new Date(), new Date()));
        System.out.println("开发者：JanYork");
        System.out.println("版本号：V2.0.0");
        System.out.println("请访问：http://localhost:" + port);
        System.out.println("文档一：http://localhost:" + port + "/swagger-ui/index.html");
        System.out.println("文档二：http://localhost:" + port + "/doc.html");
        System.out.println("数据池：http://localhost:" + port + "/druid/index.html");
    }
}