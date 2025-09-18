package Myaong.Gangajikimi.chatroom.repository;

import java.util.List;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import Myaong.Gangajikimi.chatmessage.entity.ChatMessage;
import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.chatroom.entity.QChatRoom;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomListResponse;
import Myaong.Gangajikimi.member.entity.Member;
import lombok.RequiredArgsConstructor;

import static Myaong.Gangajikimi.chatroom.entity.QChatRoom.chatRoom;
import static Myaong.Gangajikimi.chatmessage.entity.QChatMessage.chatMessage;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ChatRoomListResponse> findChatRoomsWithLastMessageAndUnreadCount(Long memberId) {
		// 내가 속한 채팅방 목록
		List<ChatRoom> rooms = queryFactory
			.selectFrom(chatRoom)
			.where(chatRoom.member1.id.eq(memberId)
				.or(chatRoom.member2.id.eq(memberId)))
			.fetch();

		return rooms.stream().map(room -> {
			// 상대방 정보
			Member partner = room.getMember1().getId().equals(memberId)
				? room.getMember2()
				: room.getMember1();

			// 최근 메시지 (최신 1개)
			ChatMessage lastMsg = queryFactory
				.selectFrom(chatMessage)
				.where(chatMessage.chatRoom.id.eq(room.getId()))
				.orderBy(chatMessage.createdAt.desc())
				.fetchFirst();

			// 안 읽은 메시지 수
			Long unreadCount = queryFactory
				.select(chatMessage.count())
				.from(chatMessage)
				.where(chatMessage.chatRoom.id.eq(room.getId())
					.and(chatMessage.sender.id.ne(memberId)) // 내가 보낸 게 아닌 메시지
					.and(chatMessage.readFlag.eq(false)))    // 안 읽은 메시지
				.fetchOne();

			return ChatRoomListResponse.builder()
				.chatroomId(room.getId())
				.partnerId(partner.getId())
				.partnerNickname(partner.getMemberName()) // Member 엔티티에는 nickname 없으므로 memberName 사용
				.lastMessage(lastMsg != null ? lastMsg.getContent() : null)
				.lastMessageTime(lastMsg != null ? lastMsg.getCreatedAt() : null)
				.unreadCount(unreadCount != null ? unreadCount : 0L)
				.build();
		}).toList();
	}

	@Override
	public Optional<ChatRoom> findByMembers(Member member1, Member member2) {
		QChatRoom chatRoom = QChatRoom.chatRoom;

		ChatRoom result = queryFactory
			.selectFrom(chatRoom)
			.where(
				(chatRoom.member1.eq(member1).and(chatRoom.member2.eq(member2)))
					.or(chatRoom.member1.eq(member2).and(chatRoom.member2.eq(member1)))
			)
			.fetchOne();

		return Optional.ofNullable(result);
	}

	// @Override
	// public Optional<ChatRoom> findByIdAndParticipant(Long chatroomId, Long userId) {
	// 	QChatRoom chatRoom = QChatRoom.chatRoom;
	//
	// 	ChatRoom result = queryFactory
	// 		.selectFrom(chatRoom)
	// 		.where(
	// 			chatRoom.id.eq(chatroomId)
	// 				.and(chatRoom.member1.id.eq(userId)
	// 					.or(chatRoom.member2.id.eq(userId)))
	// 		)
	// 		.fetchOne();
	//
	// 	return Optional.ofNullable(result);
	// }
	//
	// @Override
	// public List<ChatRoom> findAllActiveByUser(Long userId) {
	// 	QChatRoom chatRoom = QChatRoom.chatRoom;
	//
	// 	return queryFactory
	// 		.selectFrom(chatRoom)
	// 		.where(
	// 			chatRoom.member1.id.eq(userId)
	// 				.and(chatRoom.deletedByUser1.isFalse())
	// 				.or(
	// 					chatRoom.member2.id.eq(userId)
	// 						.and(chatRoom.deletedByUser2.isFalse())
	// 				)
	// 		)
	// 		.fetch();
	// }
}