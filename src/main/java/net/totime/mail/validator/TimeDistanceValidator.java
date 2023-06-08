/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.validator;

import net.totime.mail.annotation.TimeDistance;
import net.totime.mail.util.DateUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 时间距离校验
 * @see ConstraintValidator
 * @since 1.0.0
 */
public class TimeDistanceValidator implements ConstraintValidator<TimeDistance, Date> {

    private int distance;
    private ChronoUnit unit;

    @Override
    public void initialize(TimeDistance constraintAnnotation) {
        this.distance = constraintAnnotation.distance();
        this.unit = constraintAnnotation.unit();
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.ofInstant(value.toInstant(), ZoneOffset.ofHours(8)));
        return duration.compareTo(Duration.of(distance, unit)) > 0;
    }
}
