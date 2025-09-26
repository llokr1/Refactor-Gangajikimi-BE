package Myaong.Gangajikimi.chatmessage.web.docs;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessagePagingRequest;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Message", description = "메시지 관련 API")
public interface ChatMessageControllerDocs {

    @Operation(
        summary = "메시지 조회 (무한스크롤)",
        description = "특정 채팅방의 메시지 히스토리를 페이지네이션을 통해 조회합니다. 무한스크롤 방식으로 구현되어 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "메시지 조회 성공",
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
                                "messages": [
                                    {
                                        "messageId": 1,
                                        "senderId": 1,
                                        "senderNickname": "사용자1",
                                        "content": "안녕하세요!",
                                        "createdAt": "2024-01-01T12:00:00",
                                        "isRead": true
                                    }
                                ],
                                "hasNext": true,
                                "nextCursor": "2024-01-01T11:59:59"
                            }
                        }
                        """,
                    description = "result: ChatMessageFinalResponse 객체 - messages(메시지 배열), hasNext(다음 페이지 존재 여부), nextCursor(다음 페이지 커서)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> getMessages(
        @PathVariable Long chatroomId,
        @Valid @ModelAttribute ChatMessagePagingRequest request
    );

    @Operation(
        summary = "메시지 읽음 처리",
        description = "특정 메시지를 읽음 상태로 변경합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "읽음 처리 성공",
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
                    description = "result: null - 읽음 처리 성공 시 별도 데이터 없음"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> markAsRead(@PathVariable Long messageId);

    @Operation(
        summary = "메시지 삭제",
        description = "특정 메시지를 삭제합니다. 본인이 작성한 메시지만 삭제할 수 있습니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "메시지 삭제 성공",
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
                    description = "result: null - 메시지 삭제 성공 시 별도 데이터 없음"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> deleteMessage(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long messageId
    );
}
