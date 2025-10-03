package Myaong.Gangajikimi.chatroom.repository;

import java.util.List;
import java.util.Optional;

import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.chatroom.web.dto.ChatRoomListResponse;
import Myaong.Gangajikimi.common.enums.PostType;
import Myaong.Gangajikimi.member.entity.Member;

public interface ChatRoomRepositoryCustom {
	List<ChatRoomListResponse> findChatRoomsWithLastMessageAndUnreadCount(Long memberId);
	Optional<ChatRoom> findByMembersAndPost(Member member1, Member member2, PostType postType, Long postId);
	// Optional<ChatRoom> findByIdAndParticipant(Long chatroomId, Long userId);
	// List<ChatRoom> findAllActiveByUser(Long userId);
}

