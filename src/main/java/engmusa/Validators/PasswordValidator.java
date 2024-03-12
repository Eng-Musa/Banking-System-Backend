package engmusa.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        Set<String> errorMessages = new HashSet<>();

        Pattern uppercasePattern = Pattern.compile("(?=.*[A-Z])");
        Matcher uppercaseMatcher = uppercasePattern.matcher(password);

        if (!uppercaseMatcher.find()) {
            errorMessages.add("Password should contain at least one uppercase letter");
        }

        // Enforce at least one lowercase letter
        Pattern lowercasePattern = Pattern.compile("(?=.*[a-z])");
        Matcher lowercaseMatcher = lowercasePattern.matcher(password);

        if (!lowercaseMatcher.find()) {
            errorMessages.add("Password should contain at least one lowercase letter");
        }

        // Enforce at least one digit
        Pattern digitPattern = Pattern.compile("(?=.*\\d)");
        Matcher digitMatcher = digitPattern.matcher(password);

        if (!digitMatcher.find()) {
            errorMessages.add("Password should contain at least one digit");
        }

        // Enforce at least one special character
        Pattern specialCharPattern = Pattern.compile("(?=.*[@#$%^&*()_+={}|\\[\\]\\-:;'\"<>,.?/\\\\])");
        Matcher specialCharMatcher = specialCharPattern.matcher(password);

        if (!specialCharMatcher.find()) {
            errorMessages.add("Password should contain at least one special character");
        }

        if (!errorMessages.isEmpty()) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            errorMessages.forEach(
                    message -> constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                            .addConstraintViolation()
            );
        }

        return errorMessages.isEmpty();
    }
}
