package Myaong.Gangajikimi.chatroom.service;

import static Myaong.Gangajikimi.common.response.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Myaong.Gangajikimi.chatmessage.repository.ChatMessageRepository;
import Myaong.Gangajikimi.chatroom.converter.ChatRoomConverter;
import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.chatroom.repository.ChatRoomRepository;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomCreateRequest;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomListResponse;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomResponse;
import Myaong.Gangajikimi.chatroom.web.dto.delete.ChatRoomDeleteResponse;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;
	private final ChatRoomConverter converter;
	private final ChatMessageRepository chatMessageRepository;


	@Override
	@Transactional
	// 채팅방 생성
	public ChatRoomResponse createChatRoom(ChatRoomCreateRequest req, Long memberId) {
		Member member1 = memberRepository.findById(memberId)
			.orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));
		Member member2 = memberRepository.findById(req.getMemberId())
			.orElseThrow(() -> new GeneralException(MEMBER_NOT_FOUND));

		// 자기 자신과 채팅방 개설은 불가능
		if (member1 == member2) {
			throw new GeneralException(NOT_SAME_PEOPLE);
		}

		// 이미 채팅방이 존재하면 존재하던 채팅방 반환
		return chatRoomRepository.findByMembers(member1, member2)
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

	// // 채팅방 삭제 (soft)
	// @Override
	// @Transactional
	// public ChatRoomDeleteResponse softDeleteChatRoom(Long chatroomId, Long requesterId) {
	// 	ChatRoom chatRoom = chatRoomRepository.findByIdAndParticipant(chatroomId, requesterId)
	// 		.orElseThrow(() -> new GeneralException(ErrorCode.CHATROOM_NOT_FOUND));
	//
	// 	// 소프트 삭제 플래그 변경
	// 	chatRoom.softDelete(requesterId);
	//
	// 	// 두 명 모두 삭제했으면 메시지까지 완전 삭제
	// 	if (chatRoom.isFullyDeleted()) {
	// 		chatMessageRepository.deleteByChatRoomId(chatroomId);
	// 		chatRoomRepository.delete(chatRoom);
	// 	}
	//
	// 	return converter.toDeleteResponse(chatRoom, requesterId);
	// }
}

