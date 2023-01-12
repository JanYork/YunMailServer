package cn.totime.common.sms.enums;

/**
 * @author JanYork
 * @date 2023/1/12 13:02
 * @description 短信模板枚举
 */
public enum TemplateEnum {
    /**
     * 验证码
     */
    CODE("1624579", "验证码");

    private final String id;
    private final String desc;

    TemplateEnum(String id, String name) {
        this.id = id;
        this.desc = name;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}