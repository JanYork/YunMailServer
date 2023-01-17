package cn.totime.common.auth;

/**
 * @author JanYork
 * @date 2023/1/17 14:09
 * @description 第三方登录类型枚举
 */
public enum AuthType {
    /**
     * 码云
     */
    GITEE("gitee", "码云"),
    /**
     * Github
     */
    GITHUB("github", "Github"),
    /**
     * QQ
     */
    QQ("qq", "QQ"),
    /**
     * 支付宝
     */
    ALIPAY("alipay", "支付宝"),
    /**
     * 抖音
     */
    DOUYIN("douyin", "抖音");

    private final String name;
    private final String desc;

    AuthType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}