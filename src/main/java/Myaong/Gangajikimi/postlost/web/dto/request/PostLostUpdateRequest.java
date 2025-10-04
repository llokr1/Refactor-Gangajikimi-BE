package Myaong.Gangajikimi.postlost.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLostUpdateRequest {

    @NotNull
    String title; // 제목

    @NotNull
    String dogName; // 강아지 이름

    @NotNull
    String dogType; // 강아지 종류

    @NotNull
    String dogColor; // 강아지 색깔

    @NotNull
    String dogGender; // 강아지 성별

    String features; // 강아지 기타 특징

    @NotNull
    LocalDate lostDate; // 잃어버린 날짜

    @NotNull
    LocalDateTime lostTime; // 잃어버린 시간

    @NotNull
    private double lostLongitude; // 경도

    @NotNull
    private double lostLatitude;  // 위도

    // 이미지 관련 필드들
    private List<String> existingImageUrls; // 유지할 기존 이미지 URL들
    private List<String> deletedImageUrls;  // 삭제할 이미지 URL들

}
