/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.validator;

import lombok.SneakyThrows;
import net.totime.mail.annotation.RichTextWord;
import net.totime.mail.util.RichTextWordCountUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/24
 * @description 富文本字数校验
 * @see ConstraintValidator
 * @since 1.0.0
 */
public class RichTextWordValidator implements ConstraintValidator<RichTextWord, String> {
    private int min;
    private int max;

    @Override
    public void initialize(RichTextWord constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @SneakyThrows
    @Override
    public boolean isValid(String text, ConstraintValidatorContext constraintValidatorContext) {
        if (text == null) {
            return false;
        }
        int count = RichTextWordCountUtil.count(text);
        return count >= min && count <= max;
    }
}
