package Myaong.Gangajikimi.auth.refresh.service;

import Myaong.Gangajikimi.auth.web.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    public SignupResponse signup(SignupRequest request);

    public LoginResponse login(LoginRequest request, HttpServletResponse response);

    public TokenResponse reissueToken(HttpServletRequest request, HttpServletResponse response);

}