package Myaong.Gangajikimi.auth.web.controller;

import Myaong.Gangajikimi.auth.refresh.service.AuthService;
import Myaong.Gangajikimi.auth.web.dto.LoginRequest;
import Myaong.Gangajikimi.auth.web.dto.LoginResponse;
import Myaong.Gangajikimi.auth.web.dto.SignupRequest;
import Myaong.Gangajikimi.auth.web.dto.TokenResponse;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse> login(@RequestBody LoginRequest request, HttpServletResponse response){

        // Http 응답 객체 생성
        LoginResponse loginResponse = authService.login(request, response);

        return GlobalResponse.onSuccess(SuccessCode.OK, loginResponse);

    }

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse> signup(@RequestBody SignupRequest request){

        authService.signup(request);

        return GlobalResponse.onSuccess(SuccessCode.OK);
    }

    @PostMapping("/api/reissue")
    public ResponseEntity<GlobalResponse> reissueToken(HttpServletRequest request, HttpServletResponse response){

        TokenResponse tokenResponse = authService.reissueToken(request, response);

        return GlobalResponse.onSuccess(SuccessCode.OK, tokenResponse);
    }

}