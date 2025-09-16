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
	private Long chatroomId;
	private Long senderId;
	private String content;
}

