package engmusa.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<Name, String> {
    @Override
    public boolean isValid(String name , ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()){
            return false;
        }
        return true;
    }
}
