package Myaong.Gangajikimi.postfound.entity;
import Myaong.Gangajikimi.common.BaseEntity;
import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogStatus;
import Myaong.Gangajikimi.dogtype.entity.DogType;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.postfound.web.dto.request.PostFoundRequest;
import Myaong.Gangajikimi.templocation.entity.TempLocation;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFound extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 사용자 아이디

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_type_id", nullable = true)
    private DogType dogType; // 견종

    @Column(nullable = false)
    private String dogColor;

    @Column
    @Enumerated(EnumType.STRING)
    private DogGender dogGender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DogStatus status; // 강아지 상태 (실종, 목격, 귀가완료)

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate foundDate;

    @Column(nullable = false)
    private LocalDateTime foundTime;

    @Column(nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point foundSpot;

    @Column
    private String aiImage;

    @ElementCollection
    private List<String> realImage;

    @OneToMany(mappedBy = "postFound", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TempLocation> tempLocations = new ArrayList<>();

    @Builder
    private PostFound(List<String> realImage,
                      Member member,
                      String title,
                      DogType dogType,
                      String dogColor,
                      String content,
                      DogGender dogGender,
                      Point foundSpot,
                      LocalDate foundDate,
                      LocalDateTime foundTime){

        this.realImage = realImage;
        this.member = member;
        this.title = title;
        this.dogType = dogType;
        this.dogGender = dogGender;
        this.dogColor = dogColor;
        this.status = DogStatus.MISSING; // 게시글 작성 시 기본값: 실종
        this.content = content;
        this.foundSpot = foundSpot;
        this.foundDate = foundDate;
        this.foundTime = foundTime;
    }

    public static PostFound of(List<String> realImage,
                               Member member,
                               String title,
                               DogType dogType,
                               DogGender dogGender,
                               String dogColor,
                               String content,
                               Point foundSpot,
                               LocalDate foundDate,
                               LocalDateTime foundTime){

        return PostFound.builder()
                .realImage(realImage)
                .member(member)
                .title(title)
                .dogType(dogType)
                .dogColor(dogColor)
                .dogGender(dogGender)
                .content(content)
                .foundSpot(foundSpot)
                .foundDate(foundDate)
                .foundTime(foundTime)
                .build();
    }

    public void update(PostFoundRequest request, Point foundSpot, DogType dogType) {

        DogGender dogGender = DogGender.valueOf(request.getDogGender());

        // 이미지 업데이트는 updateImages() 메서드로 별도 처리
        this.title = request.getTitle();
        this.dogType = dogType;
        this.dogColor = request.getDogColor();
        this.dogGender = dogGender;
        this.content = request.getFeatures();
        this.foundDate = request.getFoundDate();
        this.foundTime = request.getFoundTime();
        this.foundSpot = foundSpot;
    }

    public void updateImages(List<String> imageKeyNames) {
        this.realImage = imageKeyNames;
    }

    /**
     * 강아지 상태 변경
     */
    public void updateStatus(DogStatus status) {
        this.status = status;
    }
}

