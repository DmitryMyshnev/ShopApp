package ua.goIt.shop.config.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsExist {
    String value() default "";

    String message() default "This value already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
