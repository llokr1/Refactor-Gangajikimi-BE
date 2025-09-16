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
public class ChatRoomResponse {
	private Long chatroomId;
	private Long member1Id;
	private Long member2Id;
	private LocalDateTime createdAt;
}
