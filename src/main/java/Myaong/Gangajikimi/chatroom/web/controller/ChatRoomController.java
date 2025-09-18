package Myaong.Gangajikimi.chatroom.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.chatroom.service.ChatRoomService;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomCreateRequest;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomListResponse;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomResponse;
import Myaong.Gangajikimi.chatroom.web.dto.delete.ChatRoomDeleteResponse;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "ChatRoom", description = "채팅방 API")
@RestController
@RequestMapping("/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	@Operation(summary = "채팅방 생성", description = "두 멤버 사이의 1:1 채팅방을 생성합니다. 이미 있으면 기존 방 반환.")
	@ApiResponse(responseCode = "200", description = "성공적으로 생성/조회됨")
	@PostMapping
	public ResponseEntity<GlobalResponse> createChatRoom(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody ChatRoomCreateRequest req) {

		Long memberId = userDetails.getId();

		ChatRoomResponse response = chatRoomService.createChatRoom(req, memberId);
		return GlobalResponse.onSuccess(SuccessCode.OK, response);
	}

	@Operation(summary = "내 채팅방 목록 조회", description = "특정 멤버가 속한 모든 채팅방을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "조회 성공")
	@GetMapping("/me")
	public ResponseEntity<GlobalResponse> getChatRooms(
		@AuthenticationPrincipal CustomUserDetails userDetails) {

		Long memberId = userDetails.getId();

		List<ChatRoomListResponse> response = chatRoomService.getChatRooms(memberId);
		return GlobalResponse.onSuccess(SuccessCode.OK, response);
	}

	// @Operation(summary = "내 채팅방 삭제(soft delete)", description = "해당 유저의 채팅방 목록에서 채팅방을 삭제합니다.")
	// @ApiResponse(responseCode = "200", description = "삭제 성공")
	// @DeleteMapping("/{chatroomId}")
	// public ResponseEntity<GlobalResponse> deleteChatRoom(
	// 	@AuthenticationPrincipal CustomUserDetails userDetails,
	// 	@PathVariable Long chatroomId) {
	//
	// 	Long memberId = userDetails.getId();
	//
	// 	ChatRoomDeleteResponse response = chatRoomService.softDeleteChatRoom(chatroomId, memberId);
	// 	return GlobalResponse.onSuccess(SuccessCode.OK, response);
	// }
}


