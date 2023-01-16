package cn.totime.common.sms.enums;

/**
 * @author JanYork
 * @date 2023/1/12 17:59
 * @description 短信签名枚举
 */
public enum SignNameEnum {

    /**
     * 默认签名
     */
    DEFAULT(504281, "拾小染网络科技"),
    /**
     * 与时签名
     */
    YUN(510118, "初柒与时同行");

    private final String signName;
    private final Integer id;

    SignNameEnum(Integer id, String signName) {
        this.signName = signName;
        this.id = id;
    }

    public String getSignName() {
        return signName;
    }

    public Integer getId() {
        return id;
    }
}