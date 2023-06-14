package net.totime.mail.enums;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/13
 * @description 登录状态枚举
 * @see Enum
 * @since 1.0.0
 */
public enum LoginState {
    /**
     * 未登录
     */
    NOT_LOGIN(0),
    /**
     * 已登录
     */
    LOGGED_IN(1),
    /**
     * 登录失效
     */
    LOGIN_FAILURE(2);

    private final int state;

    LoginState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
