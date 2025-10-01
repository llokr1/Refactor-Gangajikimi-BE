package Myaong.Gangajikimi.postlost.entity;

import Myaong.Gangajikimi.common.BaseEntity;
import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogType;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.postlost.web.dto.request.PostLostRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLost extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 사용자 아이디

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 20)
    private String dogName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DogType dogType; // 견종

    @Column(nullable = false)
    private String dogColor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DogGender dogGender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate lostDate;

    @Column(nullable = false)
    private LocalDateTime lostTime; // 분실 시간

    @JoinColumn(name = "lost_spot", nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point lostSpot; // 분실 장소

    @Column
    private String aiImage; // AI 사진 (URL 또는 파일 경로)

    @ElementCollection
    private List<String> realImage; // 실제 사진 (URL 또는 파일 경로 리스트)

    @Builder
    private PostLost(List<String> realImage,
                     Member member,
                     String title,
                     String dogName,
                     DogType dogType,
                     DogGender dogGender,
                     String dogColor,
                     String content,
                     Point lostSpot,
                     LocalDate lostDate,
                     LocalDateTime lostTime){
        this.realImage = realImage;
        this.member = member;
        this.title = title;
        this.dogName = dogName;
        this.dogType = dogType;
        this.dogGender = dogGender;
        this.dogColor = dogColor;
        this.content = content;
        this.lostSpot = lostSpot;
        this.lostDate = lostDate;
        this.lostTime = lostTime;
    }

    public static PostLost of(List<String> realImage,
                              Member member,
                              String title,
                              String dogName,
                              DogType dogType,
                              DogGender dogGender,
                              String dogColor,
                              String content,
                              Point lostSpot,
                              LocalDate lostDate,
                              LocalDateTime lostTime){
        return PostLost.builder()
                .realImage(realImage)
                .member(member)
                .title(title)
                .dogName(dogName)
                .dogType(dogType)
                .dogColor(dogColor)
                .dogGender(dogGender)
                .content(content)
                .lostSpot(lostSpot)
                .lostDate(lostDate)
                .lostTime(lostTime)
                .build();
    }

    public void update(PostLostRequest request, Point lostSpot) {

        DogType dogType = DogType.valueOf(request.getDogType());
        DogGender dogGender = DogGender.valueOf(request.getDogGender());

        // TODO: 이미지 업데이트 로직은 별도 처리 필요 (MultipartFile -> String 변환)
        // this.realImage = request.getDogImages();
        this.title = request.getTitle();
        this.dogName = request.getDogName();
        this.dogType = dogType;
        this.dogColor = request.getDogColor();
        this.dogGender = dogGender;
        this.content = request.getFeatures();
        this.lostDate = request.getLostDate();
        this.lostTime = request.getLostTime();
        this.lostSpot = lostSpot;
    }

    public void updateImages(List<String> imageKeyNames) {
        this.realImage = imageKeyNames;
    }
}