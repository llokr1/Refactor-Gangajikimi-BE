package Myaong.Gangajikimi.auth.web.docs;

import Myaong.Gangajikimi.auth.web.dto.LoginRequest;
import Myaong.Gangajikimi.auth.web.dto.SignupRequest;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthControllerDocs {

    @Operation(
        summary = "로그인",
        description = "이메일과 비밀번호를 통해 로그인을 수행합니다. 성공 시 AccessToken과 RefreshToken을 반환합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "로그인 성공",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GlobalResponse.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                            "isSuccess": true,
                            "code": "COMMON200",
                            "message": "SUCCESS!",
                            "result": {
                                "userId": 1,
                                "memberName": "홍길동",
                                "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                            }
                        }
                        """,
                    description = "result: LoginResponse 객체 - userId(사용자 ID), memberName(회원명), accessToken(액세스 토큰), refreshToken(리프레시 토큰)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> login(@RequestBody LoginRequest request, HttpServletResponse response);

    @Operation(
        summary = "회원가입",
        description = "새로운 회원을 등록합니다. 이메일, 비밀번호, 닉네임, 성별, 생년월일 정보가 필요합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "회원가입 성공",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GlobalResponse.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                            "isSuccess": true,
                            "code": "COMMON200",
                            "message": "SUCCESS!",
                            "result": null
                        }
                        """,
                    description = "result: null - 회원가입 성공 시 별도 데이터 없음"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> signup(@RequestBody SignupRequest request);

    @Operation(
        summary = "토큰 재발급",
        description = "RefreshToken을 사용하여 새로운 AccessToken을 발급받습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "토큰 재발급 성공",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GlobalResponse.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                            "isSuccess": true,
                            "code": "COMMON200",
                            "message": "SUCCESS!",
                            "result": {
                                "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                                "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                            }
                        }
                        """,
                    description = "result: TokenResponse 객체 - accessToken(새로운 액세스 토큰), refreshToken(새로운 리프레시 토큰)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> reissueToken(HttpServletRequest request, HttpServletResponse response);
}
