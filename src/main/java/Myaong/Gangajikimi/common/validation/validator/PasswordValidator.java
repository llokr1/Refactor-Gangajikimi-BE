package Myaong.Gangajikimi.common.validation.validator;

import Myaong.Gangajikimi.common.validation.annotation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        // 1. 길이 검증 (8-20자)
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }

        // 2. 문자 종류 검증 (영문 대소문자, 숫자, 특수문자 중 3가지 이상)
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        int typeCount = 0;
        if (hasUpperCase) typeCount++;
        if (hasLowerCase) typeCount++;
        if (hasDigit) typeCount++;
        if (hasSpecialChar) typeCount++;

        if (typeCount < 3) {
            return false;
        }


        return true;
    }
}
