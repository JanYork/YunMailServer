/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.handler;

import com.alibaba.fastjson2.JSON;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import lombok.extern.slf4j.Slf4j;
import net.totime.mail.dto.LetterChangeDTO;
import net.totime.mail.dto.MailChangeDTO;
import net.totime.mail.entity.*;
import net.totime.mail.enums.GlobalState;
import net.totime.mail.exception.RuntimeExceptionToMsgException;
import net.totime.mail.pojo.BaiDuAiBack;
import net.totime.mail.service.*;
import net.totime.mail.sms.tencent.TencentSmsOption;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/18
 * @description 百度AI处理
 * @since 1.0.0
 */
@Component
@Slf4j
public class BaiDuAiHandler {
    @Resource
    private AipContentCensor ai;
    @Resource
    private LetterService letterService;
    @Resource
    private MessageService messageService;
    @Resource
    private WishService wishService;
    @Resource
    private CommentService commentService;
    @Resource
    private TencentSmsOption tso;
    @Resource
    private UserService userService;

    /**
     * 信件内容审核
     *
     * @param letter 信
     */
    public void letterAiCheck(Letter letter) {
        String text = letter.getLetterTitle() + letter.getLetterContent();
        BaiDuAiBack baiDuAiBack = JSON.parseObject(ai.textCensorUserDefined(text).toString(), BaiDuAiBack.class);
        boolean compliance = baiDuAiBack.isCompliance();
        if (compliance) {
            letter.setState(GlobalState.WAITING_FOR_DELIVERY.getState());
            Boolean smsR;
            // 判断信件是否发给自己
            if (letter.getIsYourself()) {
                smsR = tso.sendLetterOk(letter.getPhone(), String.valueOf(letter.getLetterId()), letter.getGoToTime());
            } else {
                User user = userService.getById(letter.getUserId());
                smsR = tso.sendLetterOk(user.getPhone(), String.valueOf(letter.getLetterId()), letter.getGoToTime());
                if (!tso.sendLetterReceiverNotice(letter.getPhone(), letter.getGoToTime())) {
                    throw new RuntimeExceptionToMsgException("通知信件收信人失败，方法：sendLetterReceiverNotice");
                }
            }
            if (!smsR) {
                throw new RuntimeExceptionToMsgException("信件成功投递通知失败，方法：sendLetterOk");
            }
        } else {
            List<BaiDuAiBack.Data> data = baiDuAiBack.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size() && i < 3; i++) {
                sb.append(data.get(i).getMsg()).append(";");
            }
            letter.setState(GlobalState.MANUAL_REVIEW.getState());
            letter.setAiCheckMsg(sb.toString());
            boolean r = letterService.updateById(letter);
            if (!r) {
                throw new RuntimeExceptionToMsgException("信件审核失败");
            }
            String uPhone;
            if (letter.getIsYourself()) {
                uPhone = letter.getPhone();
            } else {
                User user = userService.getById(letter.getUserId());
                uPhone = user.getPhone();
            }
            Boolean smsR = tso.sendLetterAiReview(uPhone, String.valueOf(letter.getLetterId()), letter.getGoToTime());
            if (!smsR) {
                throw new RuntimeExceptionToMsgException("人工复审提醒失败，方法：sendLetterAiReview");
            }
        }
    }

    /**
     * 邮件内容审核
     *
     * @param mail 邮件
     */
    public Mail mailAiCheck(Mail mail) {
        String text = mail.getMailTitle() + mail.getMailContent();
        BaiDuAiBack baiDuAiBack = JSON.parseObject(ai.textCensorUserDefined(text).toString(), BaiDuAiBack.class);
        boolean compliance = baiDuAiBack.isCompliance();
        if (!compliance) {
            List<BaiDuAiBack.Data> data = baiDuAiBack.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size() && i < 3; i++) {
                sb.append(data.get(i).getMsg()).append(";");
            }
            mail.setState(GlobalState.MANUAL_REVIEW.getState());
            mail.setAiCheckMsg(sb.toString());
            return mail;
        }
        mail.setState(GlobalState.WAITING_FOR_DELIVERY.getState());
        return mail;
    }

    /**
     * 短信内容审核
     *
     * @param message 短信
     */
    public void messageAiCheck(Message message) {
        String text = message.getText();
        BaiDuAiBack baiDuAiBack = JSON.parseObject(ai.textCensorUserDefined(text).toString(), BaiDuAiBack.class);
        boolean compliance = baiDuAiBack.isCompliance();
        if (!compliance) {
            List<BaiDuAiBack.Data> data = baiDuAiBack.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size() && i < 3; i++) {
                sb.append(data.get(i).getMsg()).append(";");
            }
            message.setState(GlobalState.WAITING_FOR_AUDIT.getState());
            message.setAiCheckMsg(sb.toString());
            boolean r = messageService.updateById(message);
            if (!r) {
                log.error("短信更新失败，短信ID：{}", message.getId());
                throw new RuntimeExceptionToMsgException("审核系统异常");
            }
        } else {
            message.setState(GlobalState.DELIVERED.getState());
            boolean r = messageService.updateById(message);
            if (!r) {
                log.error("短信更新失败，短信ID：{}", message.getId());
                throw new RuntimeExceptionToMsgException("系统异常");
            }
            String name = message.getIsUnnamed() ? "(匿名)" : message.getUserId().toString();
            // 审核通过，直接发送短信
            Boolean smsR = tso.sendMessageCode(message.getPhone(), name, message.getId().toString());
            if (!smsR) {
                log.error("短信通知失败，短信ID：{}", message.getId());
                throw new RuntimeExceptionToMsgException("短信通知失败，方法：sendMessageCode");
            }
        }
        // 获取用户手机号
        User user = userService.getById(message.getUserId());
        // 发送短信通知
        Boolean r = tso.sendMessageSubmit(user.getPhone(), String.valueOf(message.getId()), message.getSendTime());
        if (!r) {
            log.error("短信取信码通知失败，短信ID：{}", message.getId());
            throw new RuntimeExceptionToMsgException("短信取信码通知失败，方法：sendMessageSubmit");
        }
    }

    /**
     * 心愿内容审核
     *
     * @param wish 心愿
     */
    public void wishAiCheck(Wish wish) {
        String text = wish.getText();
        BaiDuAiBack baiDuAiBackText = JSON.parseObject(ai.textCensorUserDefined(text).toString(), BaiDuAiBack.class);
        boolean complianceText = baiDuAiBackText.isCompliance();
        String imgUrl = wish.getImage();
        BaiDuAiBack baiDuAiBackImg = null;
        if (imgUrl != null) {
            baiDuAiBackImg = JSON.parseObject(ai.imageCensorUserDefined(imgUrl, EImgType.URL, null).toString(), BaiDuAiBack.class);
        }
        // 为空默认通过
        boolean complianceImg = baiDuAiBackImg == null || baiDuAiBackImg.isCompliance();
        if (!complianceText || !complianceImg) {
            String checkMsg = null;
            if (!complianceText) {
                List<BaiDuAiBack.Data> data = baiDuAiBackText.getData();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < data.size() && i < 3; i++) {
                    sb.append(data.get(i).getMsg()).append(";");
                }
                checkMsg = sb.toString();
            }
            if (!complianceImg) {
                List<BaiDuAiBack.Data> data = baiDuAiBackImg.getData();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < data.size() && i < 3; i++) {
                    sb.append(data.get(i).getMsg()).append(";");
                }
                checkMsg = checkMsg == null ? sb.toString() : checkMsg + ";" + sb;
            }
            wish.setState(GlobalState.WAITING_FOR_AUDIT.getState());
            wish.setAiCheckMsg(checkMsg);
            boolean r = wishService.updateById(wish);
            if (!r) {
                log.error("心愿更新失败，心愿ID：{}", wish.getId());
                throw new RuntimeExceptionToMsgException("审核系统异常");
            }
        } else {
            wish.setState(GlobalState.COMPLETED.getState());
            boolean r = wishService.updateById(wish);
            if (!r) {
                log.error("心愿更新失败，心愿ID：{}", wish.getId());
                throw new RuntimeExceptionToMsgException("系统异常");
            }
            // 如果审核通过，直接发送短信
            Boolean smsR = tso.sendWishSuccess(wish.getId().toString());
            if (!smsR) {
                log.error("心愿通知失败，心愿ID：{}", wish.getId());
                throw new RuntimeExceptionToMsgException("心愿通知失败，方法：sendWishSubmit");
            }
        }
    }

    /**
     * 信件修改后内容审核
     *
     * @param letter 信
     */
    public String letterChangeAiCheck(LetterChangeDTO letter) {
        String text = letter.getLetterTitle() + letter.getLetterContent();
        BaiDuAiBack baiDuAiBack = JSON.parseObject(ai.textCensorUserDefined(text).toString(), BaiDuAiBack.class);
        if (!baiDuAiBack.isCompliance()) {
            List<BaiDuAiBack.Data> data = baiDuAiBack.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size() && i < 3; i++) {
                sb.append(data.get(i).getMsg()).append(";");
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 邮件修改后内容审核
     *
     * @param mail 邮件
     */
    public String mailChangeAiCheck(MailChangeDTO mail) {
        String text = mail.getMailTitle() + mail.getMailContent();
        BaiDuAiBack baiDuAiBack = JSON.parseObject(ai.textCensorUserDefined(text).toString(), BaiDuAiBack.class);
        if (!baiDuAiBack.isCompliance()) {
            List<BaiDuAiBack.Data> data = baiDuAiBack.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size() && i < 3; i++) {
                sb.append(data.get(i).getMsg()).append(";");
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 评论内容审核
     *
     * @param comment 评论
     */
    public void commentAiCheck(Comment comment) {
        String text = comment.getContent();
        BaiDuAiBack baiDuAiBack = JSON.parseObject(ai.textCensorUserDefined(text).toString(), BaiDuAiBack.class);
        if (!baiDuAiBack.isCompliance()) {
            List<BaiDuAiBack.Data> data = baiDuAiBack.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size() && i < 3; i++) {
                sb.append(data.get(i).getMsg()).append(";");
            }
            comment.setIsSensitive(true);
            comment.setAiCheckMsg(sb.toString());
            boolean r = commentService.updateById(comment);
            if (!r) {
                log.error("评论更新失败，评论ID：{}", comment.getId());
                throw new RuntimeExceptionToMsgException("系统异常");
            }
        }
    }

    /**
     * 图片URL审核
     *
     * @param url 图片URL
     */
    public void imageAiCheck(String url) {
        //noinspection AlibabaUndefineMagicConstant
        if (!url.startsWith("http")) {
            throw new RuntimeExceptionToMsgException("图片URL不合法");
        }
        BaiDuAiBack baiDuAiBack = JSON.parseObject(ai.imageCensorUserDefined(url, EImgType.URL, null).toString(), BaiDuAiBack.class);
        if (!baiDuAiBack.isCompliance()) {
            List<BaiDuAiBack.Data> data = baiDuAiBack.getData();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size() && i < 3; i++) {
                sb.append(data.get(i).getMsg()).append(";");
            }
            throw new RuntimeExceptionToMsgException(sb.toString());
        }
    }
}
