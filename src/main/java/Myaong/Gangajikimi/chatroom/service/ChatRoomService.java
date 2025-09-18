package Myaong.Gangajikimi.chatroom.service;

import java.util.List;

import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomCreateRequest;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomListResponse;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomResponse;
import Myaong.Gangajikimi.chatroom.web.dto.delete.ChatRoomDeleteResponse;

public interface ChatRoomService {
	ChatRoomResponse createChatRoom(ChatRoomCreateRequest req, Long memberId);
	List<ChatRoomListResponse> getChatRooms(Long memberId);
	// ChatRoomDeleteResponse softDeleteChatRoom(Long chatroomId, Long requesterId);
}
