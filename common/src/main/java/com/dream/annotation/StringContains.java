package com.dream.annotation;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Description
 * @Author zhutao
 * @Date 2022-1-9
 */
@Documented
@Constraint(validatedBy = StringContainsValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface StringContains {

    String message() default "字符串不符合规则";

    String[] limitValues() default {};

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
