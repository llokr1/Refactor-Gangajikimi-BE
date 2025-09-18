package Myaong.Gangajikimi.config;

import java.security.Principal;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import Myaong.Gangajikimi.auth.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 클라이언트 연결 Endpoint
		registry.addEndpoint("/ws-chat")
			.setAllowedOriginPatterns("*") // 배포 시에는 도메인 제한 권장
			.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 구독 prefix (서버 → 클라이언트 브로드캐스트)
		registry.enableSimpleBroker("/sub");
		// 발행 prefix (클라이언트 → 서버 메시지)
		registry.setApplicationDestinationPrefixes("/pub");
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

				if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
					String authHeader = accessor.getFirstNativeHeader("Authorization");
					if (authHeader != null && authHeader.startsWith("Bearer ")) {
						String token = authHeader.substring(7);
						jwtTokenProvider.validateJwtToken(token);

						Long memberId = Long.valueOf(jwtTokenProvider.parseClaimsFromToken(token).getSubject());

						accessor.setUser(new StompPrincipal(String.valueOf(memberId)));
					}
				}
				return message;
			}
		});
	}

	// Principal 구현체
	static class StompPrincipal implements Principal {
		private final String name;

		public StompPrincipal(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}


