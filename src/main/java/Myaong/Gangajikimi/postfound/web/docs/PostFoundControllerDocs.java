package Myaong.Gangajikimi.postfound.web.docs;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.postfound.web.dto.request.PostFoundRequest;
import Myaong.Gangajikimi.postfoundreport.dto.PostFoundReportRequest;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "PostFound", description = "습득물 게시글 관련 API")
public interface PostFoundControllerDocs {

    @Operation(
        summary = "습득물 게시글 작성",
        description = "새로운 습득물 게시글을 작성합니다. 제목, 내용, 습득 장소, 습득 날짜, 이미지 등의 정보가 필요합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "게시글 작성 성공",
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
                                "postFoundId": 1,
                                "title": "지갑을 주웠습니다",
                                "content": "어제 지하철에서 지갑을 주웠습니다...",
                                "foundLocation": "강남역",
                                "foundDate": "2024-01-01",
                                "authorId": 1,
                                "authorNickname": "사용자1",
                                "createdAt": "2024-01-01T12:00:00"
                            }
                        }
                        """,
                    description = "result: PostFoundResponse 객체 - postFoundId(게시글 ID), title(제목), content(내용), foundLocation(습득 장소), foundDate(습득 날짜), authorId(작성자 ID), authorNickname(작성자 닉네임), createdAt(작성일시)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> postFound(
        @RequestBody PostFoundRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(
        summary = "습득물 게시글 수정",
        description = "기존 습득물 게시글을 수정합니다. 본인이 작성한 게시글만 수정할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "게시글 수정 성공",
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
                                "postFoundId": 1,
                                "title": "수정된 제목",
                                "content": "수정된 내용",
                                "updatedAt": "2024-01-01T13:00:00"
                            }
                        }
                        """,
                    description = "result: PostFoundResponse 객체 - postFoundId(게시글 ID), title(수정된 제목), content(수정된 내용), updatedAt(수정일시)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> updateFound(
        @RequestBody PostFoundRequest request,
        @PathVariable Long postFoundId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(
        summary = "습득물 게시글 삭제",
        description = "습득물 게시글을 삭제합니다. 본인이 작성한 게시글만 삭제할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "게시글 삭제 성공",
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
                    description = "result: null - 게시글 삭제 성공 시 별도 데이터 없음"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> deleteFound(
        @PathVariable Long postFoundId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(
        summary = "습득물 게시글 상세 조회",
        description = "특정 습득물 게시글의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "게시글 조회 성공",
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
                                "postFoundId": 1,
                                "title": "지갑을 주웠습니다",
                                "content": "어제 지하철에서 지갑을 주웠습니다...",
                                "foundLocation": "강남역",
                                "foundDate": "2024-01-01",
                                "authorId": 1,
                                "authorNickname": "사용자1",
                                "createdAt": "2024-01-01T12:00:00",
                                "updatedAt": "2024-01-01T12:00:00",
                                "images": ["image1.jpg", "image2.jpg"]
                            }
                        }
                        """,
                    description = "result: PostFoundDetailResponse 객체 - postFoundId(게시글 ID), title(제목), content(내용), foundLocation(습득 장소), foundDate(습득 날짜), authorId(작성자 ID), authorNickname(작성자 닉네임), createdAt(작성일시), updatedAt(수정일시), images(이미지 목록)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> getPostFoundDetail(@PathVariable Long postFoundId);

    @Operation(
        summary = "습득물 게시글 신고",
        description = "부적절한 습득물 게시글을 신고합니다. 본인의 게시글은 신고할 수 없습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "신고 성공",
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
                                "reportId": 1,
                                "postFoundId": 1,
                                "reporterId": 2,
                                "reportType": "SPAM",
                                "reportReason": "스팸 게시글입니다",
                                "createdAt": "2024-01-01T12:00:00"
                            }
                        }
                        """,
                    description = "result: PostFoundReportResponse 객체 - reportId(신고 ID), postFoundId(게시글 ID), reporterId(신고자 ID), reportType(신고 유형), reportReason(신고 사유), createdAt(신고일시)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> reportPostFound(
        @PathVariable Long postFoundId,
        @RequestBody PostFoundReportRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
