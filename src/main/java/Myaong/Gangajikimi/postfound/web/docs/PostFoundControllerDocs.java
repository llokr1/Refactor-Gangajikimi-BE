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
                                "postId": 1,
                                "memberName": "홍길동",
                                "postTitle": "강아지를 주웠습니다",
                                "postDate": "2024-01-01T12:00:00"
                            }
                        }
                        """,
                    description = "result: PostFoundResponse 객체 - postId(게시글 ID), memberName(작성자명), postTitle(제목), postDate(작성일시)"
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
                                "postId": 1,
                                "memberName": "홍길동",
                                "postTitle": "수정된 제목",
                                "postDate": "2024-01-01T13:00:00"
                            }
                        }
                        """,
                    description = "result: PostFoundResponse 객체 - postId(게시글 ID), memberName(작성자명), postTitle(수정된 제목), postDate(수정일시)"
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
                                "postId": 1,
                                "title": "강아지를 주웠습니다",
                                "dogName": "멍멍이",
                                "dogType": "MALTESE",
                                "dogColor": "흰색",
                                "dogGender": "MALE",
                                "content": "어제 공원에서 강아지를 주웠습니다...",
                                "foundDate": "2024-01-01",
                                "foundTime": "2024-01-01T14:30:00",
                                "longitude": 127.0276,
                                "latitude": 37.4979,
                                "authorId": 1,
                                "authorName": "홍길동",
                                "createdAt": "2024-01-01T12:00:00",
                                "timeAgo": "2시간 전"
                            }
                        }
                        """,
                    description = "result: PostFoundDetailResponse 객체 - postId(게시글 ID), title(제목), dogName(강아지 이름), dogType(견종), dogColor(색상), dogGender(성별), content(내용), foundDate(습득 날짜), foundTime(습득 시간), longitude(경도), latitude(위도), authorId(작성자 ID), authorName(작성자명), createdAt(작성일시), timeAgo(상대시간)"
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
                                "postId": 1,
                                "createdAt": "2024-01-01T12:00:00"
                            }
                        }
                        """,
                    description = "result: PostFoundReportResponse 객체 - postId(게시글 ID), createdAt(신고일시)"
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
