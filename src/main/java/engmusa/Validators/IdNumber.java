package engmusa.Validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdNumberValidator.class)
public @interface IdNumber {
    String message() default "Invalid ID Number!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
