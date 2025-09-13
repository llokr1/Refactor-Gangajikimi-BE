package Myaong.Gangajikimi.postlost.entity;

import Myaong.Gangajikimi.common.BaseEntity;
import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogType;
import Myaong.Gangajikimi.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
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
}