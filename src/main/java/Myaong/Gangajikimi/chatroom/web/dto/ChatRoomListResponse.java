package Myaong.Gangajikimi.chatroom.web.dto;

import java.time.LocalDateTime;

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
public class ChatRoomListResponse {
	private Long chatroomId;
	private Long partnerId;
	private String partnerNickname;
	private String lastMessage;
	private LocalDateTime lastMessageTime;
	private Long unreadCount;
}
