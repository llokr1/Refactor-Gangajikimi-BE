package Myaong.Gangajikimi.postlost.web;


import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.facade.PostLostFacade;
import Myaong.Gangajikimi.postlost.web.docs.PostLostControllerDocs;
import Myaong.Gangajikimi.postlostreport.service.PostLostReportService;
import Myaong.Gangajikimi.postlostreport.dto.PostLostReportRequest;
import Myaong.Gangajikimi.postlost.web.dto.request.PostLostRequest;

import jakarta.validation.Valid;
import Myaong.Gangajikimi.postlost.web.dto.response.PostLostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lost-posts")
public class PostLostController implements PostLostControllerDocs {

    private final PostLostFacade postLostFacade;
    private final PostLostReportService postLostReportService;


    @PostMapping
    public ResponseEntity<GlobalResponse> postLost(@Valid @RequestBody PostLostRequest request,
                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();

        return GlobalResponse.onSuccess(SuccessCode.OK,
                postLostFacade.postPostLost(request, memberId));
    }

    @PatchMapping("/{postLostId}")
    public ResponseEntity<GlobalResponse> updateLost(@Valid @RequestBody PostLostRequest request, @PathVariable Long postLostId,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();

        return GlobalResponse.onSuccess(SuccessCode.OK,
                postLostFacade.updatePostLost(request, memberId, postLostId));
    }

    @DeleteMapping("/{postLostId}")
    public ResponseEntity<GlobalResponse> deleteLost(@PathVariable Long postLostId,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();

        postLostFacade.deletePostLost(memberId, postLostId);

        return GlobalResponse.onSuccess(SuccessCode.OK);
    }

    @GetMapping("/{postLostId}")
    public ResponseEntity<GlobalResponse> getPostLostDetail(@PathVariable Long postLostId) {
        PostLostDetailResponse response = postLostFacade.getPostLostDetail(postLostId);
        return GlobalResponse.onSuccess(SuccessCode.OK, response);
    }

    @PostMapping("/{postLostId}/reports")
    public ResponseEntity<GlobalResponse> reportPostLost(@PathVariable Long postLostId,
                                                         @RequestBody PostLostReportRequest request,
                                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        Long memberId = userDetails.getId();
        var response = postLostReportService.reportPostLost(postLostId, request, memberId);
        
        return GlobalResponse.onSuccess(SuccessCode.OK, response);
    }

}
