package Myaong.Gangajikimi.auth.refresh.entity;

import Myaong.Gangajikimi.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken extends BaseEntity {

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, unique = true)
    @Setter
    private String token;

    @Builder
    private RefreshToken(Long memberId, String token){
        this.memberId = memberId;
        this.token = token;
    }

    public static RefreshToken of(Long memberId, String token){
        return RefreshToken.builder()
                .memberId(memberId)
                .token(token)
                .build();
    }

}