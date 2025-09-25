package Myaong.Gangajikimi.postfound.web.controller;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.facade.PostFoundFacade;
import Myaong.Gangajikimi.postfound.web.dto.request.PostFoundRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/found-posts")
public class PostFoundController {

    private final PostFoundFacade postFoundFacade;

    @PostMapping
    public ResponseEntity<GlobalResponse> postFound(@Valid @RequestBody PostFoundRequest request,

                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();

        return GlobalResponse.onSuccess(SuccessCode.OK, postFoundFacade.postPostFound(request, memberId));
    }

    @PatchMapping("/{postFoundId}")
    public ResponseEntity<GlobalResponse> updateFound(@Valid @RequestBody PostFoundRequest request, @PathVariable Long postFoundId,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();

        return GlobalResponse.onSuccess(SuccessCode.OK,
                postFoundFacade.updatePostFound(request, memberId, postFoundId));
    }

    @DeleteMapping("/{postFoundId}")
    public ResponseEntity<GlobalResponse> deleteFound(@PathVariable Long postFoundId,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();

        postFoundFacade.deletePostFound(memberId, postFoundId);

        return GlobalResponse.onSuccess(SuccessCode.OK);
    }
}

