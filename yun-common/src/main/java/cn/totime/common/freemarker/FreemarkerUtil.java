package cn.totime.common.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author JanYork
 * @date 2023/1/9 9:13
 * @description Freemarker处理通用工具类
 */
@Component
public class FreemarkerUtil {

//    @Value("${ftl.general-base-package-path}")
    private String generalBasePackagePath = "/templates/general";

//    @Value("${spring.freemarker.mail-base-package-name}")
    private String mailBasePackageName = "cn/totime/templates/email";

    /**
     * 根据模板和数据生成邮件内容(General)
     *
     * @param templateName 模板名称
     * @param content      模板内容
     * @return 模板HTML
     */
    @SneakyThrows({IOException.class, TemplateException.class})
    public String getTemplateContent(String templateName, Map<String, Object> content) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setClassForTemplateLoading(FreemarkerUtil.class, generalBasePackagePath);
        Template template = configuration.getTemplate(templateName);
        StringWriter stringWriter = new StringWriter();
        template.process(content, stringWriter);
        return stringWriter.toString();
    }

    /**
     * 根据模板和数据生成邮件内容(Mail)
     *
     * @param templateName 模板名称
     * @param content      模板内容
     * @return 模板HTML
     */
    @SneakyThrows({IOException.class, TemplateException.class})
    public String getTemplateContentMail(String templateName, Map<String, Object> content) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setClassForTemplateLoading(FreemarkerUtil.class, mailBasePackageName);
        Template template = configuration.getTemplate(templateName);
        StringWriter stringWriter = new StringWriter();
        template.process(content, stringWriter);
        return stringWriter.toString();
    }
}