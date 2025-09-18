package Myaong.Gangajikimi.chatmessage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Myaong.Gangajikimi.chatmessage.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	List<ChatMessage> findByChatRoomIdOrderByCreatedAtAsc(Long chatroomId);
	List<ChatMessage> findByChatRoomIdAndContentContaining(Long chatroomId, String keyword);
	void deleteByChatRoomId(Long chatroomId);
}