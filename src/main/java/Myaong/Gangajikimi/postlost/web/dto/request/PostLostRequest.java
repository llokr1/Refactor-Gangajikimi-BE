package Myaong.Gangajikimi.postlost.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLostRequest {

    @NotNull
    String title; // 제목

    @NotNull
    String dogName; // 강아지 이름

    @NotNull
    String dogType; // 견종 이름

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

}
