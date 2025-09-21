package Myaong.Gangajikimi.chatmessage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Myaong.Gangajikimi.chatmessage.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	Page<ChatMessage> findByChatRoomId(Long chatroomId, Pageable pageable);
	List<ChatMessage> findByChatRoomIdAndContentContaining(Long chatroomId, String keyword);
	void deleteByChatRoomId(Long chatroomId);
}