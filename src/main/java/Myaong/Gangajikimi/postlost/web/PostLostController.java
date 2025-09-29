package Myaong.Gangajikimi.postlost.web;


import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.dto.PageResponse;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.facade.PostLostFacade;
import Myaong.Gangajikimi.postlost.web.docs.PostLostControllerDocs;
import Myaong.Gangajikimi.postlostreport.service.PostLostReportService;
import Myaong.Gangajikimi.postlostreport.dto.PostLostReportRequest;
import Myaong.Gangajikimi.postlost.web.dto.request.PostLostRequest;
import Myaong.Gangajikimi.postlost.service.PostLostQueryService;

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
    private final PostLostQueryService postLostQueryService;


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

    @GetMapping
    public ResponseEntity<GlobalResponse> getLostPosts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        PageResponse response = postLostQueryService.getLostPosts(page, size);
        
        return GlobalResponse.onSuccess(SuccessCode.OK, response);
    }

    @GetMapping("/my-posts")
    public ResponseEntity<GlobalResponse> getMyLostPosts(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {

        Long memberId = userDetails.getId();

        PageResponse response = postLostQueryService.getMyLostPosts(memberId, page, size);
        
        return GlobalResponse.onSuccess(SuccessCode.OK, response);
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
