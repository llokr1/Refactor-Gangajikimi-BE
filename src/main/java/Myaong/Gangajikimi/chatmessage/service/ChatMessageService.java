package Myaong.Gangajikimi.chatmessage.service;

import java.util.List;

import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessageResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatSendRequest;

public interface ChatMessageService {
	void handleMessage(ChatSendRequest req);
	List<ChatMessageResponse> getMessages(Long chatroomId);
	void markAsRead(Long messageId);
}
