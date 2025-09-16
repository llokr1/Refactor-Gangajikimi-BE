package Myaong.Gangajikimi.config;

import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import Myaong.Gangajikimi.chatmessage.web.dto.ChatEventResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

	private final SimpMessagingTemplate messagingTemplate;
	private final Gson gson = new Gson();

	@Override
	public void onMessage(org.springframework.data.redis.connection.Message message, byte[] pattern) {
		String msgJson = new String(message.getBody(), StandardCharsets.UTF_8);
		ChatEventResponse event = gson.fromJson(msgJson, ChatEventResponse.class);

		// 특정 채팅방 구독자들에게 브로드캐스트
		messagingTemplate.convertAndSend("/topic/chat/" + event.getChatroomId(), event);
	}
}
