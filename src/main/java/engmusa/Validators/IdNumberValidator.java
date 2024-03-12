package engmusa.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdNumberValidator implements ConstraintValidator<IdNumber, String> {
    @Override
    public boolean isValid(String idNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(idNumber == null){
            return false;
        }
        return idNumber.length() >= 7 && idNumber.length() <= 8;
    }
}
