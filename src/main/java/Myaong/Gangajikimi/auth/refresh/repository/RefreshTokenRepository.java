package Myaong.Gangajikimi.auth.refresh.repository;

import Myaong.Gangajikimi.auth.refresh.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMemberId(Long memberId);
}