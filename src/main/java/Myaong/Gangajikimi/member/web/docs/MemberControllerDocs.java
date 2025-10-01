package Myaong.Gangajikimi.member.web.docs;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Tag(name = "Member", description = "회원 관련 API")
public interface MemberControllerDocs {

    @Operation(
        summary = "내 프로필 정보 조회",
        description = "로그인한 사용자의 프로필 정보를 조회합니다. 마이페이지에서 사용자명 표시용으로 사용됩니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "프로필 정보 조회 성공",
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
                                "memberId": 1,
                                "username": "홍길동",
                                "email": "hong@example.com"
                            }
                        }
                        """,
                    description = "result: MemberProfileResponse 객체 - memberId(회원 ID), username(사용자명), email(이메일)"
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "인증 실패",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GlobalResponse.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                            "isSuccess": false,
                            "code": "AUTH401",
                            "message": "인증이 필요합니다",
                            "result": null
                        }
                        """,
                    description = "인증 토큰이 없거나 유효하지 않은 경우"
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "회원 정보를 찾을 수 없음",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GlobalResponse.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                            "isSuccess": false,
                            "code": "MEMBER404",
                            "message": "회원을 찾을 수 없습니다",
                            "result": null
                        }
                        """,
                    description = "토큰의 회원 ID로 회원 정보를 찾을 수 없는 경우"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> getMyProfile(
        @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
