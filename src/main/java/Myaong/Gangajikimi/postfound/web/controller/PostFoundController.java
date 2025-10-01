package Myaong.Gangajikimi.postfound.web.controller;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.common.dto.PageResponse;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import Myaong.Gangajikimi.facade.PostFoundFacade;
import Myaong.Gangajikimi.postfound.web.docs.PostFoundControllerDocs;
import Myaong.Gangajikimi.postfoundreport.dto.PostFoundReportRequest;
import Myaong.Gangajikimi.postfoundreport.dto.PostFoundReportResponse;
import Myaong.Gangajikimi.postfound.web.dto.request.PostFoundRequest;
import Myaong.Gangajikimi.postfound.web.dto.response.PostFoundDetailResponse;
import Myaong.Gangajikimi.postfound.service.PostFoundQueryService;

import jakarta.validation.Valid;
import Myaong.Gangajikimi.postfoundreport.service.PostFoundReportService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/found-posts")
public class PostFoundController implements PostFoundControllerDocs {

    private final PostFoundFacade postFoundFacade;
    private final PostFoundReportService postFoundReportService;
    private final PostFoundQueryService postFoundQueryService;

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

    @GetMapping
    public ResponseEntity<GlobalResponse> getFoundPosts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        PageResponse response = postFoundQueryService.getFoundPosts(page, size);
        
        return GlobalResponse.onSuccess(SuccessCode.OK, response);
    }

    @GetMapping("/my-posts")
    public ResponseEntity<GlobalResponse> getMyFoundPosts(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        PageResponse response = postFoundQueryService.getMyFoundPosts(memberId, page, size);
        
        return GlobalResponse.onSuccess(SuccessCode.OK, response);
    }

    @GetMapping("/{postFoundId}")
    public ResponseEntity<GlobalResponse> getPostFoundDetail(@PathVariable Long postFoundId) {

        PostFoundDetailResponse response = postFoundFacade.getPostFoundDetail(postFoundId);

        return GlobalResponse.onSuccess(SuccessCode.OK, response);
    }

    @PostMapping("/{postFoundId}/reports")
    public ResponseEntity<GlobalResponse> reportPostFound(@PathVariable Long postFoundId,
                                                          @RequestBody PostFoundReportRequest request,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getId();
        PostFoundReportResponse response = postFoundReportService.reportPostFound(postFoundId, request, memberId);

        return GlobalResponse.onSuccess(SuccessCode.OK, response);
    }
}

