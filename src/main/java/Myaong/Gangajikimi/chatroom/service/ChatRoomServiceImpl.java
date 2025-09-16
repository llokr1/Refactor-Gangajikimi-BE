package Myaong.Gangajikimi.chatroom.service;

import static Myaong.Gangajikimi.common.response.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Myaong.Gangajikimi.chatroom.converter.ChatRoomConverter;
import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.chatroom.repository.ChatRoomRepository;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomCreateRequest;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomListResponse;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomResponse;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;
	private final ChatRoomConverter converter;

	@Override
	@Transactional
	// 채팅방 생성
	public ChatRoomResponse createChatRoom(ChatRoomCreateRequest req, Long memberId) {
		Member member1 = memberRepository.findById(memberId)
			.orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));
		Member member2 = memberRepository.findById(req.getMemberId())
			.orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));

		// 이미 채팅방이 존재하면 존재하던 채팅방 반환
		return chatRoomRepository.findByMember1AndMember2(member1, member2)
			.map(converter::toResponse)
			.orElseGet(() -> {
				ChatRoom newRoom = ChatRoom.builder()
					.member1(member1)
					.member2(member2)
					.build();
				return converter.toResponse(chatRoomRepository.save(newRoom));
			});
	}

	// 채팅방 목록 조회
	@Override
	@Transactional(readOnly = true)
	public List<ChatRoomListResponse> getChatRooms(Long memberId) {
		return chatRoomRepository.findChatRoomsWithLastMessageAndUnreadCount(memberId);
	}
}

