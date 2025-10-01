package Myaong.Gangajikimi.postfound.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostFoundRequest {

    List<MultipartFile> dogImages; // 강아지 실제 사진들

    @NotNull
    String title; // 제목

    @NotNull
    String dogType; // 견종 이름

    @NotNull
    String dogColor; // 강아지 색깔

    @NotNull
    String dogGender; // 강아지 성별

    String features; // 강아지 기타 특징

    @NotNull
    LocalDate foundDate; // 목격한 날짜

    @NotNull
    LocalDateTime foundTime; // 목격한 시간

    @NotNull
    private double foundLongitude; // 경도

    @NotNull
    private double foundLatitude;  // 위도

}
