package cn.totime.common.sms.enums;

/**
 * @author JanYork
 * @date 2023/1/12 13:02
 * @description 短信模板枚举
 */
public enum TemplateEnum {
    /**
     * 新的工单通知
     */
    NEW_WORK_TICKETS("1670370", "新的工单通知"),
    /**
     * 工单处理结果通知
     */
    WORK_TICKETS_RESULT("1670369", "工单处理结果通知"),
    /**
     * 服务端错误通知
     */
    SERVER_ERROR("1670368", "服务端错误通知"),
    /**
     * 今日信件代发通知
     */
    YUN_TODAY_LETTER("1670367", "云寄今日信件代发通知"),
    /**
     * 用户信息不合规通知
     */
    USER_INFO_NOT_CONFORMITY("1670366", "用户信息不合规通知"),
    /**
     * 实名变更通知
     */
    REAL_NAME_CHANGE("1670365", "实名变更通知"),
    /**
     * 账户封禁通知
     */
    ACCOUNT_BANNED("1670364", "账户封禁通知"),
    /**
     * 许愿失败通知
     */
    WISH_FAILED("1670362", "许愿失败通知"),
    /**
     * 匿名信投递失败
     */
    ANONYMOUS_LETTER_FAILED("1670361", "匿名信投递失败"),
    /**
     * 匿名信投递收信人通知
     */
    ANONYMOUS_LETTER_RECEIVER("1670360", "匿名信投递收信人通知"),
    /**
     * 短信发送完成通知
     */
    SMS_SEND_COMPLETE("1670359", "短信发送完成通知"),
    /**
     * 短信投递通知
     */
    SMS_DELIVERY("1670358", "短信投递成功通知"),
    /**
     * 定时邮件收件人通知
     */
    TIMING_EMAIL_RECEIVER("1670357", "定时邮件收件人通知"),
    /**
     * 信件收件人通知
     */
    LETTER_RECEIVER("1670356", "信件收件人通知"),
    /**
     * 信件被退回通知
     */
    LETTER_RETURNED("1670354", "信件被退回通知"),
    /**
     * 信件被签收通知
     */
    LETTER_SIGNED("1670352", "信件被签收通知"),
    /**
     * 信件发出通知
     */
    LETTER_SEND("1670351", "信件发出通知"),
    /**
     * 信件审核问题通知
     */
    LETTER_AUDIT_PROBLEM("1670349", "信件审核问题通知"),
    /**
     * 信件审核通过通知
     */
    LETTER_AUDIT_PASS("1670348", "信件审核通过(投递成功)通知"),
    /**
     * 修改手机绑定验证码
     */
    MODIFY_PHONE("1670347", "修改手机绑定验证码"),
    /**
     * 重置密码验证码
     */
    RESET_PASSWORD("1670346", "重置密码验证码"),
    /**
     * 登录验证码
     */
    LOGIN("1670346", "登录验证码"),
    /**
     * 注册验证码
     */
    REGISTER("1670271", "注册验证码");

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