package Myaong.Gangajikimi.postlost.web.dto.response;

import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogStatus;
import Myaong.Gangajikimi.dogtype.entity.DogType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostLostDetailResponse {

    private Long postId;
    private String title;
    private String dogName;
    private String dogType;
    private String dogColor;
    private DogGender dogGender;
    private DogStatus dogStatus;
    private String content;
    private LocalDate lostDate;
    private LocalDateTime lostTime;
    private Double longitude;
    private Double latitude;
    // TODO: 주소 변환 API 연동 후 활성화 (예: "서울시 송파구")
    // private String address;
    // TODO: AI 이미지 생성 로직 구현 후 활성화
    // private String aiImage; // AI 생성 이미지 Presigned URL
    private List<String> realImages; // 실제 이미지 Presigned URL 목록
    private Long authorId;
    private String authorName;
    private LocalDateTime createdAt;
    private String timeAgo;

    @Builder
    private PostLostDetailResponse(Long postId, String title, String dogName, String dogType,
                                  String dogColor, DogGender dogGender, DogStatus dogStatus, String content,
                                  LocalDate lostDate, LocalDateTime lostTime, Double longitude, Double latitude,
                                  // TODO: 주소 변환 API 연동 후 활성화
                                  // String address,
                                  // TODO: AI 이미지 생성 로직 구현 후 활성화
                                  // String aiImage, 
                                  List<String> realImages,
                                  Long authorId, String authorName,
                                  LocalDateTime createdAt, String timeAgo) {
        this.postId = postId;
        this.title = title;
        this.dogName = dogName;
        this.dogType = dogType;
        this.dogColor = dogColor;
        this.dogGender = dogGender;
        this.dogStatus = dogStatus;
        this.content = content;
        this.lostDate = lostDate;
        this.lostTime = lostTime;
        this.longitude = longitude;
        this.latitude = latitude;
        // TODO: 주소 변환 API 연동 후 활성화
        // this.address = address;
        // TODO: AI 이미지 생성 로직 구현 후 활성화
        // this.aiImage = aiImage;
        this.realImages = realImages;
        this.authorId = authorId;
        this.authorName = authorName;
        this.createdAt = createdAt;
        this.timeAgo = timeAgo;
    }

    public static PostLostDetailResponse of(Long postId, String title, String dogName, String dogType,
                                          String dogColor, DogGender dogGender, DogStatus dogStatus, String content,
                                          LocalDate lostDate, LocalDateTime lostTime, Double longitude, Double latitude,
                                          // TODO: 주소 변환 API 연동 후 활성화
                                          // String address,
                                          // TODO: AI 이미지 생성 로직 구현 후 활성화
                                          // String aiImage, 
                                          List<String> realImages,
                                          Long authorId, String authorName,
                                          LocalDateTime createdAt, String timeAgo) {
        return PostLostDetailResponse.builder()
                .postId(postId)
                .title(title)
                .dogName(dogName)
                .dogType(dogType)
                .dogColor(dogColor)
                .dogGender(dogGender)
                .dogStatus(dogStatus)
                .content(content)
                .lostDate(lostDate)
                .lostTime(lostTime)
                .longitude(longitude)
                .latitude(latitude)
                // TODO: 주소 변환 API 연동 후 활성화
                // .address(address)
                // TODO: AI 이미지 생성 로직 구현 후 활성화
                // .aiImage(aiImage)
                .realImages(realImages)
                .authorId(authorId)
                .authorName(authorName)
                .createdAt(createdAt)
                .timeAgo(timeAgo)
                .build();
    }
}
