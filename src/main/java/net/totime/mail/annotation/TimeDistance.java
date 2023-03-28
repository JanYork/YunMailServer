package net.totime.mail.annotation;

import net.totime.mail.validator.TimeDistanceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 时间距离校验
 * @see Annotation
 * @since 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {TimeDistanceValidator.class})
public @interface TimeDistance {
    String message() default "时间距今过短";
    int distance() default 24;
    ChronoUnit unit() default ChronoUnit.HOURS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
