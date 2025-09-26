package Myaong.Gangajikimi.common.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import Myaong.Gangajikimi.common.validation.validator.NicknameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NicknameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNickname {
	String message() default "이미 사용 중인 닉네임입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
