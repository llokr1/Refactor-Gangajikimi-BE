package Myaong.Gangajikimi.member.web.dto.response;

import Myaong.Gangajikimi.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 프로필 정보 응답 DTO
 * 
 * 마이페이지에서 사용할 회원의 기본 정보를 반환합니다.
 */
@Getter
@NoArgsConstructor
public class MemberProfileResponse {

    private String username;
    
    @Builder
    private MemberProfileResponse(String username) {
        this.username = username;
    }

    /**
     * Member 엔티티를 MemberProfileResponse로 변환
     */
    public static MemberProfileResponse from(String memberName) {
        return MemberProfileResponse.builder()
            .username(memberName)
            .build();
    }
}
