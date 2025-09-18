package Myaong.Gangajikimi.chatroom.web.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDeleteRequest {
	private Long chatroomId;
	private Long requesterId;
}
