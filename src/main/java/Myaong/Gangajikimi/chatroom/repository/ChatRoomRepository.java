package Myaong.Gangajikimi.chatroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Myaong.Gangajikimi.chatroom.entity.ChatRoom;
import Myaong.Gangajikimi.member.entity.Member;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
	Optional<ChatRoom> findByMember1AndMember2(Member member1, Member member2);

}
