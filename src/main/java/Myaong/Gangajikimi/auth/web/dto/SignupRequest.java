package Myaong.Gangajikimi.auth.web.dto;

import Myaong.Gangajikimi.common.validation.annotation.ValidEmail;
import Myaong.Gangajikimi.common.validation.annotation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "닉네임은 필수입니다.")
    public String memberName;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효하지 않은 이메일 주소예요")
    @ValidEmail
    public String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @ValidPassword
    public String password;

}