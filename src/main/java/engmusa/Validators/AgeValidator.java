package engmusa.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {
    @Override
    public boolean isValid(LocalDate dateOfBirth,
                           ConstraintValidatorContext context) {
        Set<String> errorMessages = new HashSet<>();
        LocalDate today = LocalDate.now();
        int age = Period.between(dateOfBirth, today).getYears();
        try{
            if (age < 18){
                errorMessages.add("You must be greater than 18 years of age.");;
            }
        }catch (Exception e) {
            errorMessages.add("Invalid date format. Please use the format yyyy-MM-dd.");
        }

        if (!errorMessages.isEmpty()) {
            context.disableDefaultConstraintViolation();
            errorMessages.forEach(message -> context.buildConstraintViolationWithTemplate(message).addConstraintViolation());
        }

        return errorMessages.isEmpty();
    }
}
