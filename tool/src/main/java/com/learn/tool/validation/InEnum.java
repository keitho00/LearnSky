package com.learn.tool.validation;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Lixiaochun
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = InEnumValidatorImpl.class)
public @interface InEnum {

    Class<? extends Enum> clazz();

    String valueField() default "value";

    String message() default "value is not valid";

    Class[] groups() default {};

    Class[] payload() default {};

}
