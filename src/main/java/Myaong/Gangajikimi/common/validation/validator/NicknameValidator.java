package Myaong.Gangajikimi.common.validation.validator;

import Myaong.Gangajikimi.common.validation.annotation.ValidNickname;
import Myaong.Gangajikimi.member.service.MemberService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NicknameValidator implements ConstraintValidator<ValidNickname, String> {

	private final MemberService memberService;

	@Override
	public boolean isValid(String nickname, ConstraintValidatorContext context) {
		if (nickname == null || nickname.trim().isEmpty()) {
			return true; // null이나 빈 문자열은 다른 validation에서 처리
		}
		
		// 닉네임이 이미 존재하는지 확인
		return !memberService.isNicknameDuplicate(nickname);
	}
}
