package Myaong.Gangajikimi.postfound.web.controller;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.facade.PostFoundFacade;
import Myaong.Gangajikimi.postfound.web.dto.request.PostFoundRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/found-posts")
public class PostFoundController {

    private final PostFoundFacade postFoundFacade;

    @PostMapping
    public ResponseEntity<GlobalResponse> postLost(@RequestBody PostFoundRequest request,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();

        return GlobalResponse.onSuccess(SuccessCode.OK, postFoundFacade.postPostFound(request, memberId));
    }
}
