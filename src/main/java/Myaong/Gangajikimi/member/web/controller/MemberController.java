package Myaong.Gangajikimi.member.web.controller;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.member.service.MemberService;
import Myaong.Gangajikimi.member.web.docs.MemberControllerDocs;
import Myaong.Gangajikimi.member.web.dto.response.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    @GetMapping("/profiles")
    public ResponseEntity<GlobalResponse> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        Long memberId = userDetails.getId();
        
        return GlobalResponse.onSuccess(SuccessCode.OK,
                MemberProfileResponse.from(memberService.findMemberById(memberId).getMemberName()));
    }
}
