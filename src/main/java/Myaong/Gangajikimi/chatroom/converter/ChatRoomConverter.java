package Myaong.Gangajikimi.chatroom.converter;

import org.springframework.stereotype.Component;

import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomResponse;
import Myaong.Gangajikimi.chatroom.web.dto.delete.ChatRoomDeleteResponse;

@Component
public class ChatRoomConverter {
	// 채팅방 생성 혹은 존재하던 채팅방 반환 시 response
	public ChatRoomResponse toResponse(ChatRoom room) {
		return ChatRoomResponse.builder()
			.chatroomId(room.getId())
			.member1Id(room.getMember1().getId())
			.member2Id(room.getMember2().getId())
			.createdAt(room.getCreatedAt())
			.build();
	}

}

