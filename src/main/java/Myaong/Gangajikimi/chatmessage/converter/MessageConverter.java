package Myaong.Gangajikimi.chatmessage.converter;

import org.springframework.stereotype.Component;

import Myaong.Gangajikimi.chatmessage.entity.ChatMessage;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatEventResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessageResponse;

@Component
public class MessageConverter {
	// 메세지 조회 시 response
	public ChatMessageResponse toResponse(ChatMessage m) {
		return ChatMessageResponse.builder()
			.messageId(m.getId())
			.chatroomId(m.getChatRoom().getId())
			.senderId(m.getSender().getId())
			.content(m.getContent())
			.createdAt(m.getCreatedAt())
			.read(m.getReadFlag())
			.build();
	}

	public ChatEventResponse toEvent(ChatMessage m) {
		return ChatEventResponse.builder()
			.messageId(m.getId())
			.chatroomId(m.getChatRoom().getId())
			.senderId(m.getSender().getId())
			.content(m.getContent())
			.createdAt(m.getCreatedAt())
			.read(m.getReadFlag())
			.build();
	}
}

