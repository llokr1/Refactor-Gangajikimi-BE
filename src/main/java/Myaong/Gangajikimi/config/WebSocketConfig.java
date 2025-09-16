package Myaong.Gangajikimi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 클라이언트 연결 Endpoint
		registry.addEndpoint("/ws-chat")
			.setAllowedOriginPatterns("*"); // 배포 시에는 도메인 제한 권장
			// .withSockJS(); // SockJS fallback 지원 -> postman 에서 테스트하기 위해 주석처리
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 구독 prefix (서버 → 클라이언트 브로드캐스트)
		registry.enableSimpleBroker("/topic");

		// 발행 prefix (클라이언트 → 서버 메시지)
		registry.setApplicationDestinationPrefixes("/app");
	}
}


