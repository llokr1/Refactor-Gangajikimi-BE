package Myaong.Gangajikimi.chatmessage.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.chatmessage.service.ChatMessageService;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessageFinalResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessagePagingRequest;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessageResponse;
import Myaong.Gangajikimi.common.response.GlobalResponse;
import Myaong.Gangajikimi.common.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Message", description = "메시지 API")
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class ChatMessageController {
	private final ChatMessageService messageService;

	@Operation(summary = "메시지 조회(무한스크롤)", description = "특정 채팅방의 메시지 히스토리를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "조회 성공")
	@GetMapping("/{chatroomId}")
	public ResponseEntity<GlobalResponse> getMessages(
		@PathVariable Long chatroomId,
		@Valid @ModelAttribute ChatMessagePagingRequest request) {

		ChatMessageFinalResponse response =
			messageService.getMessages(chatroomId, request);

		return GlobalResponse.onSuccess(SuccessCode.OK, response);
	}

	@Operation(summary = "메시지 읽음 처리", description = "특정 메시지를 읽음 처리합니다.")
	@ApiResponse(responseCode = "200", description = "읽음 처리 성공")
	@PatchMapping("/{messageId}/read")
	public ResponseEntity<GlobalResponse> markAsRead(@PathVariable Long messageId) {
		messageService.markAsRead(messageId);
		return GlobalResponse.onSuccess(SuccessCode.OK);
	}

	@Operation(summary = "특정 메시지 삭제 API", description = "특정 메시지를 삭제합니다.")
	@ApiResponse(responseCode = "200", description = "메세지 삭제 성공")
	@DeleteMapping("/{messageId}")
	public ResponseEntity<GlobalResponse> deleteMessage(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long messageId) {
		Long memberId = userDetails.getId();
		messageService.deleteMessage(messageId,memberId);
		return GlobalResponse.onSuccess(SuccessCode.OK);
	}

	// @Operation(summary = "메시지 검색 API", description = "메세지를 검색합니다.")
	// @ApiResponse(responseCode = "200", description = "메세지 검색 성공")
	// @GetMapping("/{messageId}")
	// public ResponseEntity<GlobalResponse> searchMessages(
	// 	@RequestParam Long chatroomId,
	// 	@RequestParam String keyword) {
	//
	// 	List<ChatSearchResponse> response = messageService.searchMessage(chatroomId, keyword);
	//
	// 	return GlobalResponse.onSuccess(SuccessCode.OK, response);
	// }

}

