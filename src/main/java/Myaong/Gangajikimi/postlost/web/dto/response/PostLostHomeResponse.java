package Myaong.Gangajikimi.postlost.web.dto.response;

import Myaong.Gangajikimi.postlost.entity.PostLost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 잃어버렸어요 게시글 홈 화면 응답 DTO
 * 
 * 홈 화면에서 잃어버렸어요 게시글 목록을 표시하기 위한 응답 DTO입니다.
 * 게시글의 기본 정보와 함께 분실 시간 정보를 포함합니다.
 */
@Getter
@NoArgsConstructor
public class PostLostHomeResponse {
    
    private Long id;
    private String title;
    private String dogType;
    private String dogColor;
    private String location; // 행정동/구 단위
    private LocalDateTime lostDateTime;
    private String image;
    private String status; // TODO: 상태 필드 추가 예정
    
    @Builder
    private PostLostHomeResponse(Long id, String title, String dogType, String dogColor,
                                String location, LocalDateTime lostDateTime,
                                String image, String status) {
        this.id = id;
        this.title = title;
        this.dogType = dogType;
        this.dogColor = dogColor;
        this.location = location;
        this.lostDateTime = lostDateTime;
        this.image = image;
        this.status = status;
    }

    /**
     * PostLost 엔티티를 PostLostHomeResponse로 변환
     */
    public static PostLostHomeResponse from(PostLost postLost) {
        return PostLostHomeResponse.builder()
            .id(postLost.getId())
            .title(postLost.getTitle())
            .dogType(postLost.getDogType().getType())
            .dogColor(postLost.getDogColor())
            .location("TODO: 행정동/구 단위 위치 정보") // TODO: 위치 정보 변환 로직 추가
            .lostDateTime(postLost.getLostTime())
            .image(postLost.getRealImage() != null && !postLost.getRealImage().isEmpty() 
                ? postLost.getRealImage().get(0) : null) // 첫 번째 이미지 사용
            .status("상태") // TODO: 상태 필드 구현
            .build();
    }
}
