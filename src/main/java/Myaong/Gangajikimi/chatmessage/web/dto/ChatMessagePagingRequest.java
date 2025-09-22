package Myaong.Gangajikimi.chatmessage.web.dto;

import Myaong.Gangajikimi.common.validation.annotation.ValidPage;
import Myaong.Gangajikimi.common.validation.annotation.ValidSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessagePagingRequest {

	@ValidPage
	private int page;

	@ValidSize
	private int size;
}

