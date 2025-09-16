package Myaong.Gangajikimi.chatmessage.web.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import Myaong.Gangajikimi.chatmessage.service.ChatMessageService;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatSendRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Chat WebSocket", description = "실시간 채팅 WebSocket API")
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
	private final ChatMessageService messageService;

	@MessageMapping("/chat.send")
	public void sendMessage(ChatSendRequest req, Principal principal) {
		messageService.handleMessage(req);
	}
}
