package Myaong.Gangajikimi.chatmessage.service;

import static Myaong.Gangajikimi.common.response.ErrorCode.*;

import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import Myaong.Gangajikimi.chatmessage.converter.MessageConverter;
import Myaong.Gangajikimi.chatmessage.entity.ChatMessage;
import Myaong.Gangajikimi.chatmessage.repository.MessageRepository;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatEventResponse;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatSendRequest;
import Myaong.Gangajikimi.chatmessage.web.dto.ChatMessageResponse;
import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.chatroom.repository.ChatRoomRepository;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
	private final MessageRepository messageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;
	private final MessageConverter converter;
	private final StringRedisTemplate redisTemplate;
	private final Gson gson = new Gson();

	@Transactional
	// 메세지 전송
	public void handleMessage(ChatSendRequest req) {
		ChatRoom room = chatRoomRepository.findById(req.getChatroomId())
			.orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));
		Member sender = memberRepository.findById(req.getSenderId())
			.orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));

		ChatMessage msg = ChatMessage.builder()
			.chatRoom(room)
			.sender(sender)
			.content(req.getContent())
			.readFlag(false)
			.build();

		ChatMessage saved = messageRepository.save(msg);

		ChatEventResponse event = converter.toEvent(saved);
		redisTemplate.convertAndSend("chat:room:" + room.getId(), gson.toJson(event));
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
}
