package cn.totime.common.sms.enums;

/**
 * @author JanYork
 * @date 2023/1/12 17:44
 * @description 短信应用枚举
 */
public enum SdkAppEnum {
    /**
     * 默认短信应用
     */
    SDK_APP_ID("1400751895", "短信默认应用"),
    /**
     * 云寄短信应用
     */
    YUN_SDK_APP_ID("1400791247", "云寄短信应用");

    private final String id;
    private final String desc;

    SdkAppEnum(String id, String name) {
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