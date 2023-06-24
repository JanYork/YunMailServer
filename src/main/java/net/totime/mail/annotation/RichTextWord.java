/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.annotation;

import net.totime.mail.validator.RichTextWordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 富文本字数校验
 * @see Annotation
 * @since 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RichTextWordValidator.class})
public @interface RichTextWord {
    String message() default "字数不符合要求";

    int min() default 0;

    int max() default 5000;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
