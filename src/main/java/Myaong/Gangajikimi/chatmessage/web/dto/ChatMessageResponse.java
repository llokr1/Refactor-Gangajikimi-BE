package Myaong.Gangajikimi.chatmessage.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// MessageResponse.java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageResponse {
	private Long messageId;
	private Long chatroomId;
	private Long senderId;
	private String content;
	private LocalDateTime createdAt;
	private Boolean read;
}
