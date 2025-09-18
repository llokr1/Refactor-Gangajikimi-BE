package Myaong.Gangajikimi.chatmessage.web.dto;

import Myaong.Gangajikimi.chatmessage.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSearchResponse {
	private Long messageId;
	private Long senderId;
	private String content;

	public static ChatSearchResponse fromEntity(ChatMessage message) {
		return ChatSearchResponse.builder()
			.messageId(message.getId())
			.senderId(message.getSender().getId())
			.content(message.getContent())
			.build();
	}
}
