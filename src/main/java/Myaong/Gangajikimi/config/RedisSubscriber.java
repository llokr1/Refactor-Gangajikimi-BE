package Myaong.Gangajikimi.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import Myaong.Gangajikimi.chatmessage.web.dto.ChatEventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper;
	private final SimpMessagingTemplate messagingTemplate;

	/*
	* RedisSubscriber.onMessage에서 받은 이벤트를
	* messagingTemplate.convertAndSend("/sub/chatroom/{roomId}", event)로 브로드캐스트.
👉  * 결과: 같은 채팅방에 연결된 모든 클라이언트가 새 메시지를 실시간으로 받음.
	* */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			String msg = new String(message.getBody());
			ChatEventResponse event = objectMapper.readValue(msg, ChatEventResponse.class);
			messagingTemplate.convertAndSend("/sub/chatroom/" + event.getChatroomId(), event);
		} catch (Exception e) {
			log.error("Redis 구독 메시지 처리 중 오류", e);
		}
	}
}
