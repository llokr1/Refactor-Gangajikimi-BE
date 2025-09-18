package Myaong.Gangajikimi.chatmessage.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// ChatSendRequest.java (WebSocket 메시지 전송 요청)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatSendRequest {
	private String type;        // "AUTH" | "MESSAGE"
	private Long chatroomId;    // MESSAGE일 때만 사용
	private String token;       // AUTH일 때 사용
	private String content;     // MESSAGE일 때 사용
}
