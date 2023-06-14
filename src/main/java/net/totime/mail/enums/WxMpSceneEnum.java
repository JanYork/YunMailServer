package net.totime.mail.enums;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/12
 * @description 微信公众号场景枚举
 * @see Enum
 * @since 1.0.0
 */
public enum WxMpSceneEnum {
    /**
     * 登录
     */
    LOGIN("login", "登录");
    private final String scene;
    private final String desc;

    WxMpSceneEnum(String scene, String desc) {
        this.scene = scene;
        this.desc = desc;
    }

    public String getScene() {
        return scene;
    }

    public String getDesc() {
        return desc;
    }

    public static WxMpSceneEnum getEnum(String scene) {
        for (WxMpSceneEnum wxMpSceneEnum : WxMpSceneEnum.values()) {
            if (wxMpSceneEnum.getScene().equals(scene)) {
                return wxMpSceneEnum;
            }
        }
        return null;
    }
}
