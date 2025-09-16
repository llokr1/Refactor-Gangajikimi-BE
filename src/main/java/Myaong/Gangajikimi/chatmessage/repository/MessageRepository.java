package Myaong.Gangajikimi.chatmessage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Myaong.Gangajikimi.chatmessage.entity.ChatMessage;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
	List<ChatMessage> findByChatRoomIdOrderByCreatedAtAsc(Long chatroomId);
}