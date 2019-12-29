package com.bill.unifeedback.validator;

import javax.validation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ReCaptchaConstraintValidator.class)
@Target({ TYPE,METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidReCaptcha {

    String message() default "Invalid ReCaptcha";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}