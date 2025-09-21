package Myaong.Gangajikimi.chatmessage.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageFinalResponse {
	private List<ChatMessageResponse> messages;
	private boolean hasNext;
}
