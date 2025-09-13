package Myaong.Gangajikimi.auth.refresh.service;

import Myaong.Gangajikimi.auth.refresh.entity.RefreshToken;
import Myaong.Gangajikimi.auth.refresh.repository.RefreshTokenRepository;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveOrUpdateRefreshToken(Member member, String refreshToken){

        Long memberId = member.getId();

        // 없으면 null 처리
        RefreshToken storedRefreshToken = refreshTokenRepository.findByMemberId(member.getId())
                .orElse(null);

        // null이라면 (없다면)
        if (storedRefreshToken == null){

            // 토큰 새로 생성
            RefreshToken newRefreshToken = RefreshToken.of(memberId, refreshToken);
            // 저장
            refreshTokenRepository.save(newRefreshToken);
            return;
        }
        // 존재한다면
        storedRefreshToken.setToken(refreshToken);
    }

    public RefreshToken findRefreshTokenByMemberId(Long memberId){

        RefreshToken member = refreshTokenRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));

        return member;
    }


}