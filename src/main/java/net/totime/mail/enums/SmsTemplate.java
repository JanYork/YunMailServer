/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.enums;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/12
 * @description 短信模板枚举
 * @see Enum
 * @since 1.0.0
 */
public enum SmsTemplate {
    /**
     * 一般验证码
     */
    NORMAL("1837323", "一般验证码"),
    /**
     * 系统错误通知
     */
    SYSTEM_ERROR_NOTICE("1842745", "系统错误通知"),
    /**
     * 信件内容AI过滤后人工复审通知
     */
    LETTER_AI_REVIEW("1837675", "信件内容AI过滤后人工复审通知"),
    /**
     * 信件收件人通知(代发出)
     */
    LETTER_RECEIVER_NOTICE("1837681", "信件收件人通知"),
    /**
     * 信件驳回通知
     */
    LETTER_REJECT("1760442", "信件驳回"),
    /**
     * 信件收件方通知
     */
    LETTER_RECEIVER("1760441", "信件收件方通知"),
    /**
     * 信件退回通知
     */
    LETTER_RETURN("1760438", "信件退回通知"),
    /**
     * 许愿成功通知
     */
    WISH_SUCCESS("1842331", "许愿成功通知"),
    /**
     * 手机改绑验证码
     */
    PHONE_CHANGE("1760437", "手机改绑验证码"),
    /**
     * 信件发出通知
     */
    LETTER_SEND("1760433", "信件发出通知"),
    /**
     * 实名变更通知
     */
    REAL_NAME_CHANGE("1760432", "实名变更通知"),
    /**
     * 信息发出提醒
     */
    INFO_SEND("1760431", "信息发出提醒"),
    /**
     * 账户信息不合规通知
     */
    ACCOUNT_INFO("1760430", "账户信息不合规通知"),
    /**
     * 运营每日报表
     */
    OPERATE_DAILY("1760429", "运营每日报表"),
    /**
     * 注册验证码
     */
    REGISTER("1760428", "注册验证码"),
    /**
     * 信件签收通知
     */
    LETTER_SIGN("1760427", "信件签收通知"),
    /**
     * 取信码通知
     */
    LETTER_CODE("1760426", "取信码通知"),
    /**
     * 短信投递成功通知
     */
    SMS_DELIVERY("1760425", "短信投递成功通知"),
    /**
     * 系统错误通知
     */
    SYSTEM_ERROR("1760424", "系统错误通知"),
    /**
     * 信件投递成功通知
     */
    LETTER_DELIVERY("1760422", "信件投递成功通知"),
    /**
     * 登录验证码
     */
    LOGIN("1760421", "登录验证码"),
    /**
     * 信息驳回通知
     */
    SMS_REJECT("1760420", "信息驳回通知"),
    /**
     * 账户封禁通知
     */
    USER_REJECT("1760419", "账户封禁通知"),
    /**
     * 重置密码
     */
    RESET_PASSWORD("1760418", "重置密码"),
    /**
     * 许愿驳回通知
     */
    WISH_REJECT("1760416", "许愿驳回通知"),
    /**
     * 绑定手机号验证码
     */
    BIND_PHONE("1760412", "绑定手机号验证码");
    private final String templateId;
    private final String templateName;

    SmsTemplate(String templateId, String templateName) {
        this.templateId = templateId;
        this.templateName = templateName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getTemplateName() {
        return templateName;
    }
}
