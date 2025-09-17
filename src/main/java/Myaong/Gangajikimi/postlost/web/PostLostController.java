package Myaong.Gangajikimi.postlost.web;


import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.facade.PostLostFacade;
import Myaong.Gangajikimi.postlost.web.dto.request.PostLostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lost-posts")
public class PostLostController {

    private final PostLostFacade postLostFacade;


    @PostMapping
    public ResponseEntity<GlobalResponse> postLost(@RequestBody PostLostRequest request,
                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();

        return GlobalResponse.onSuccess(SuccessCode.OK,
                postLostFacade.postPostLost(request, memberId));
    }



}
