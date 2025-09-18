package Myaong.Gangajikimi.chatroom.web.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDeleteResponse {
	private Long chatroomId;
	private boolean deletedForRequester;
	private boolean fullyDeleted;
}
