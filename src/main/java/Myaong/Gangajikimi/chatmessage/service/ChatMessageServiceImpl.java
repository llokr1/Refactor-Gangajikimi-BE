package Myaong.Gangajikimi.chatmessage.service;

import static Myaong.Gangajikimi.common.response.ErrorCode.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Myaong.Gangajikimi.chatmessage.converter.MessageConverter;
import Myaong.Gangajikimi.chatmessage.entity.ChatMessage;
import Myaong.Gangajikimi.chatmessage.repository.ChatMessageRepository;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatEventResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatSendRequest;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessageResponse;
import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.chatroom.repository.ChatRoomRepository;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {
	private final ChatMessageRepository messageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;
	private final MessageConverter converter;
	private final StringRedisTemplate redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Transactional
	// 메세지 전송
	public ChatEventResponse handleMessage(ChatSendRequest req, Long senderId)  {
		log.info("📩 받은 요청: type={}, chatroomId={}, content={}",
			req.getType(), req.getChatroomId(), req.getContent());
		ChatRoom room = chatRoomRepository.findById(req.getChatroomId())
			.orElseThrow(() -> new GeneralException(CHATROOM_NOT_FOUND));

		Member sender = memberRepository.findById(senderId)
			.orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));

		ChatMessage msg = ChatMessage.builder()
			.chatRoom(room)
			.sender(sender)
			.content(req.getContent())
			.readFlag(false)
			.build();

		ChatMessage saved = messageRepository.save(msg);

		ChatEventResponse event = converter.toEvent(saved);
		// 6. Redis Pub/Sub 전송 (JSON 직렬화)
		try {
			String payload = objectMapper.writeValueAsString(event);
			redisTemplate.convertAndSend("chat:room:" + room.getId(), payload);
		} catch (JsonProcessingException e) {
			// 직렬화 실패 시 로그 남기고 런타임 예외 던짐
			throw new RuntimeException("메시지 직렬화 실패: " + e.getMessage(), e);
		}

		// 7. 결과 반환
		return event;
	}

	// 메세지 조회
	@Transactional(readOnly = true)
	public List<ChatMessageResponse> getMessages(Long chatroomId) {
		return messageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatroomId)
			.stream().map(converter::toResponse).toList();
	}

	// 읽음 처리 표시
	@Transactional
	public void markAsRead(Long messageId) {
		ChatMessage msg = messageRepository.findById(messageId)
			.orElseThrow(() -> new GeneralException(MESSAGE_NOT_FOUND));
		msg.changeReadFlag(true);
		messageRepository.save(msg);
	}

	// 메세지 삭제
	@Transactional
	public void deleteMessage(Long messageId, Long memberId) {

		ChatMessage msg = messageRepository.findById(messageId)
			.orElseThrow(() -> new GeneralException(MESSAGE_NOT_FOUND));

		if (!msg.getSender().getId().equals(memberId)) {
			throw new GeneralException(CANNOT_DELETE_MASSAGE);
		}
		messageRepository.deleteById(messageId);
	}

	// // 메세지 검색
	// @Transactional
	// public List<ChatSearchResponse> searchMessage(Long chatRoomId, String keyword) {
	//
	// 	List<ChatMessage> messages = messageRepository
	// 		.findByChatRoomIdAndContentContaining(chatRoomId, keyword);
	//
	// 	return messages.stream()
	// 		.map(ChatSearchResponse::fromEntity)
	// 		.toList();
	// }
}
