package Myaong.Gangajikimi.postfound.web.dto.response;

import Myaong.Gangajikimi.postfound.entity.PostFound;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 목격했어요 게시글 홈 화면 응답 DTO
 * 
 * 홈 화면에서 목격했어요 게시글 목록을 표시하기 위한 응답 DTO입니다.
 * 게시글의 기본 정보와 함께 목격 시간 정보를 포함합니다.
 */
@Getter
@NoArgsConstructor
public class PostFoundHomeResponse {
    
    private Long id;
    private String title;
    private String dogType;
    private String dogColor;
    private String location; // 행정동/구 단위
    private LocalDateTime foundDateTime;
    private String image;
    private String status; // TODO: 상태 필드 추가 예정
    
    @Builder
    private PostFoundHomeResponse(Long id, String title, String dogType, String dogColor,
                                 String location, LocalDateTime foundDateTime,
                                 String image, String status) {
        this.id = id;
        this.title = title;
        this.dogType = dogType;
        this.dogColor = dogColor;
        this.location = location;
        this.foundDateTime = foundDateTime;
        this.image = image;
        this.status = status;
    }

    /**
     * PostFound 엔티티를 PostFoundHomeResponse로 변환
     */
    public static PostFoundHomeResponse from(PostFound postFound) {
        return PostFoundHomeResponse.builder()
            .id(postFound.getId())
            .title(postFound.getTitle())
            .dogType(postFound.getDogType().getType())
            .dogColor(postFound.getDogColor())
            .location("TODO: 행정동/구 단위 위치 정보") // TODO: 위치 정보 변환 로직 추가
            .foundDateTime(postFound.getFoundTime())
            .image(postFound.getRealImage() != null && !postFound.getRealImage().isEmpty() 
                ? postFound.getRealImage().get(0) : null) // 첫 번째 이미지 사용
            .status("상태") // TODO: 상태 필드 구현
            .build();
    }
}
