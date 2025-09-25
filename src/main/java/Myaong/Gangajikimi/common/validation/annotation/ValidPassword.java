package Myaong.Gangajikimi.common.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import Myaong.Gangajikimi.common.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidPassword {
    String message() default "비밀번호는 8-20자이며, 영문 대소문자, 숫자, 특수문자 중 3가지 이상을 포함해야 합니다.";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
