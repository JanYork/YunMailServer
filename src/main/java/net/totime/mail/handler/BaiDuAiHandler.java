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
import net.totime.mail.dto.LetterChangeDTO;
import net.totime.mail.entity.Letter;
import net.totime.mail.entity.User;
import net.totime.mail.enums.GlobalState;
import net.totime.mail.exception.RuntimeExceptionToMsgException;
import net.totime.mail.pojo.BaiDuAiBack;
import net.totime.mail.service.LetterService;
import net.totime.mail.service.UserService;
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
public class BaiDuAiHandler {
    @Resource
    private AipContentCensor ai;
    @Resource
    private LetterService letterService;
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
            Boolean smsR = tso.sendLetterAiReview(letter.getPhone(), String.valueOf(letter.getLetterId()), letter.getGoToTime());
            if (!smsR) {
                throw new RuntimeExceptionToMsgException("人工复审提醒失败，方法：sendLetterAiReview");
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
}
