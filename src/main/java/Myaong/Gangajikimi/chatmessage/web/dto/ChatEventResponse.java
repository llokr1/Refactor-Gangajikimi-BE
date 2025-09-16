package Myaong.Gangajikimi.chatmessage.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// ChatEventResponse.java (WebSocket으로 브로드캐스트되는 이벤트)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatEventResponse {
	private Long messageId;
	private Long chatroomId;
	private Long senderId;
	private String content;
	private LocalDateTime createdAt;
	private Boolean read;
}
