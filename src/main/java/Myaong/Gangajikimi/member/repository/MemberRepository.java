package Myaong.Gangajikimi.member.repository;

import Myaong.Gangajikimi.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);
	@NonNull
	Optional<Member> findById(@NonNull Long memberId);
	boolean existsByEmail(String email);
}
