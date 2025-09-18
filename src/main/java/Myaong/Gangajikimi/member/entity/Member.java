package Myaong.Gangajikimi.member.entity;

import Myaong.Gangajikimi.auth.web.dto.SignupRequest;
import Myaong.Gangajikimi.common.BaseEntity;
import Myaong.Gangajikimi.common.enums.AccountStatus;
import Myaong.Gangajikimi.common.enums.Gender;
import Myaong.Gangajikimi.common.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Column(nullable = false)
	private String memberName;

	@Column(nullable = false)
	private String email;

	@Column
	private String password;

	@Column(nullable = false)
	@ColumnDefault("'USER'")
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(nullable = false)
	@ColumnDefault("'ACTIVATED'")
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;

	@Builder
	private Member(String memberName, String email, String password, String gender, Integer age, String address, String specAddress){

		this.memberName = memberName;
		this.email = email;
		this.password = password;
		this.role = Role.USER;
		this.accountStatus = AccountStatus.ACTIVATED;
	}

	public static Member of(SignupRequest request, String encryptedPassword){

		return Member.builder()
			.memberName(request.getMemberName())
			.email(request.getEmail())
			.password(encryptedPassword)
			.build();
	}


}
