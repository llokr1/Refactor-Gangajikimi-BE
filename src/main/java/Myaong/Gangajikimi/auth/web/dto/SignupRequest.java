package Myaong.Gangajikimi.auth.web.dto;

import Myaong.Gangajikimi.common.validation.annotation.ValidNickname;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
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
    @ValidNickname
    public String memberName;

    @NotNull
    @Email(message = "유효하지 않은 이메일 주소예요")
    public String email;

    @NotNull
    public String password;


}