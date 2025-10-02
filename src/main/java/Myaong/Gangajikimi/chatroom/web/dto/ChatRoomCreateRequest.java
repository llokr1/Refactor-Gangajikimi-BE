package Myaong.Gangajikimi.chatroom.web.dto;

import Myaong.Gangajikimi.common.enums.PostType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomCreateRequest {
	@NotNull(message = "멤버 ID는 필수입니다.")
	private Long memberId;

	@NotNull
	private PostType postType; // LOST | FOUND

	@NotNull
	private Long postId;

}