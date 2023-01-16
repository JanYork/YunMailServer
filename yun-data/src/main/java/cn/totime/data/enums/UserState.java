package cn.totime.data.enums;

/**
 * @author JanYork
 * @date 2023/1/16 16:40
 * @description 用户状态枚举
 */
public enum UserState {
    /**
     * 正常状态
     */
    NORMAL(0, "正常"),
    /**
     * 冻结状态
     */
    LOCKED(1, "冻结"),
    /**
     * 封禁状态
     */
    DISABLED(2, "禁用"),
    /**
     * 删除状态
     */
    DELETED(3, "删除"),
    /**
     * 未激活状态
     */
    UNACTIVATED(4, "未激活");


    private final Integer code;
    private final String desc;

    UserState(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}