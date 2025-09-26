package Myaong.Gangajikimi.chatroom.web.docs;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomCreateRequest;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "ChatRoom", description = "채팅방 관련 API")
public interface ChatRoomControllerDocs {

    @Operation(
        summary = "채팅방 생성",
        description = "두 멤버 사이의 1:1 채팅방을 생성합니다. 이미 존재하는 채팅방이 있으면 기존 방을 반환합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "채팅방 생성/조회 성공",
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
                                "chatroomId": 1,
                                "memberId1": 1,
                                "memberId2": 2,
                                "member1Nickname": "사용자1",
                                "member2Nickname": "사용자2",
                                "createdAt": "2024-01-01T00:00:00"
                            }
                        }
                        """,
                    description = "result: ChatRoomResponse 객체 - chatroomId(채팅방 ID), memberId1/memberId2(참여자 ID), member1Nickname/member2Nickname(참여자 닉네임), createdAt(생성일시)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> createChatRoom(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody ChatRoomCreateRequest req
    );

    @Operation(
        summary = "내 채팅방 목록 조회",
        description = "현재 로그인한 사용자가 참여한 모든 채팅방 목록을 조회합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "채팅방 목록 조회 성공",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = GlobalResponse.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = """
                        {
                            "isSuccess": true,
                            "code": "COMMON200",
                            "message": "SUCCESS!",
                            "result": [
                                {
                                    "chatroomId": 1,
                                    "otherMemberId": 2,
                                    "otherMemberNickname": "사용자2",
                                    "lastMessage": "안녕하세요!",
                                    "lastMessageTime": "2024-01-01T12:00:00",
                                    "unreadCount": 3
                                }
                            ]
                        }
                        """,
                    description = "result: ChatRoomListResponse 배열 - chatroomId(채팅방 ID), otherMemberId(상대방 ID), otherMemberNickname(상대방 닉네임), lastMessage(마지막 메시지), lastMessageTime(마지막 메시지 시간), unreadCount(안읽은 메시지 수)"
                )
            )
        )
    })
    ResponseEntity<GlobalResponse> getChatRooms(@AuthenticationPrincipal CustomUserDetails userDetails);
}
