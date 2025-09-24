package Myaong.Gangajikimi.postfound.web.dto.response;

import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostFoundDetailResponse {

    private Long postId;
    private String title;
    private String dogName;
    private DogType dogType;
    private String dogColor;
    private DogGender dogGender;
    private String content;
    private LocalDate foundDate;
    private LocalDateTime foundTime;
    private String foundLocation;
    private String aiImage;
    private List<String> realImages;
    private String authorName;
    private LocalDateTime createdAt;
    private String timeAgo;

    @Builder
    private PostFoundDetailResponse(Long postId, String title, String dogName, DogType dogType, 
                                   String dogColor, DogGender dogGender, String content,
                                   LocalDate foundDate, LocalDateTime foundTime, String foundLocation,
                                   String aiImage, List<String> realImages, String authorName,
                                   LocalDateTime createdAt, String timeAgo) {
        this.postId = postId;
        this.title = title;
        this.dogName = dogName;
        this.dogType = dogType;
        this.dogColor = dogColor;
        this.dogGender = dogGender;
        this.content = content;
        this.foundDate = foundDate;
        this.foundTime = foundTime;
        this.foundLocation = foundLocation;
        this.aiImage = aiImage;
        this.realImages = realImages;
        this.authorName = authorName;
        this.createdAt = createdAt;
        this.timeAgo = timeAgo;
    }

    public static PostFoundDetailResponse of(Long postId, String title, String dogName, DogType dogType,
                                           String dogColor, DogGender dogGender, String content,
                                           LocalDate foundDate, LocalDateTime foundTime, String foundLocation,
                                           String aiImage, List<String> realImages, String authorName,
                                           LocalDateTime createdAt, String timeAgo) {
        return PostFoundDetailResponse.builder()
                .postId(postId)
                .title(title)
                .dogName(dogName)
                .dogType(dogType)
                .dogColor(dogColor)
                .dogGender(dogGender)
                .content(content)
                .foundDate(foundDate)
                .foundTime(foundTime)
                .foundLocation(foundLocation)
                .aiImage(aiImage)
                .realImages(realImages)
                .authorName(authorName)
                .createdAt(createdAt)
                .timeAgo(timeAgo)
                .build();
    }
}
