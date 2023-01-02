package cn.totime.config.swagger;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

/**
 * @author JanYork
 * @date 2022/12/27 19:34
 * @description Swagger3配置类
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enable;

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.license}")
    private String license;

    @Value("${swagger.licenseUrl}")
    private String licenseUrl;

    @Value("${swagger.version}")
    private String version;

    @Value("${swagger.contact.name}")
    private String name;

    @Value("${swagger.contact.url}")
    private String url;

    @Value("${swagger.contact.email}")
    private String email;


    /**
     * Swagger3文档信息
     *
     * @return ApiInfo API信息对象
     */
    @Bean
    public ApiInfo createApiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .contact(new Contact(name, url, email))
                .license(license)
                .licenseUrl(licenseUrl)
                .build();
    }

    /**
     * 分组-全部接口
     *
     * @return Docket Docket对象
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable(enable)
                .apiInfo(createApiInfo())
                .groupName("全部接口")
                .select()
                .paths(Predicate.not(Predicate.isEqual("/error")))
                .apis(input -> true)
                .build();
    }

    /**
     * 分组-用户接口
     */
    @Bean
    public Docket createUserRestApi() {
        //创建paths过滤路径对象
        Predicate<String> paths = path -> path.startsWith("/user") || path.startsWith("/admin");
        return new Docket(DocumentationType.OAS_30)
                .enable(enable)
                .apiInfo(createApiInfo())
                .groupName("用户接口")
                .select()
                .paths(paths)
                .build();
    }
}