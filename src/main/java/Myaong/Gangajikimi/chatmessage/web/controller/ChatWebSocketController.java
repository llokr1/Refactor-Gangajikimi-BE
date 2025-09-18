package Myaong.Gangajikimi.chatmessage.web.controller;

import java.security.Principal;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import Myaong.Gangajikimi.auth.jwt.JwtTokenProvider;
import Myaong.Gangajikimi.auth.userDetails.CustomUserDetails;
import Myaong.Gangajikimi.chatmessage.service.ChatMessageService;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatEventResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatSendRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Chat WebSocket", description = "실시간 채팅 WebSocket API")
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
	private final ChatMessageService messageService;
	private final JwtTokenProvider jwtTokenProvider;
	private final SimpMessagingTemplate messagingTemplate;

	// 세션별 인증 사용자 매핑
	private static final ConcurrentHashMap<String, Long> sessionAuthMap = new ConcurrentHashMap<>();

	@MessageMapping("/chat")
	public void handle(ChatSendRequest req, Principal principal) {
		if ("AUTH".equals(req.getType())) {
			Long memberId = jwtTokenProvider.extractMemberIdFromRefreshToken(req.getToken());
			sessionAuthMap.put(principal.getName(), memberId);

		} else if ("MESSAGE".equals(req.getType())) {
			Long senderId = sessionAuthMap.get(principal.getName());
			messageService.handleMessage(req, senderId);
		}
	}
}
