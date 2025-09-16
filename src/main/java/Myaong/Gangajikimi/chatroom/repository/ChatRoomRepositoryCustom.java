package Myaong.Gangajikimi.chatroom.repository;

import java.util.List;

import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomListResponse;

public interface ChatRoomRepositoryCustom {
	List<ChatRoomListResponse> findChatRoomsWithLastMessageAndUnreadCount(Long memberId);
}

