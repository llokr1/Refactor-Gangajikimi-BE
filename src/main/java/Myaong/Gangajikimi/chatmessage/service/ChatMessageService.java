package Myaong.Gangajikimi.chatmessage.service;

import java.util.List;

import Myaong.Gangajikimi.chatmessage.web.dto.ChatEventResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessageResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatSearchResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatSendRequest;

public interface ChatMessageService {
	ChatEventResponse handleMessage(ChatSendRequest req, Long senderId);
	List<ChatMessageResponse> getMessages(Long chatroomId);
	void markAsRead(Long messageId);
	void deleteMessage(Long messageId, Long memberId);
	// List<ChatSearchResponse> searchMessage(Long chatRoomId, String keyword);
}
