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
    DEFAULT("拾小染网络科技");

    private final String signName;

    SignNameEnum(String signName) {
        this.signName = signName;
    }

    public String getSignName() {
        return signName;
    }
}