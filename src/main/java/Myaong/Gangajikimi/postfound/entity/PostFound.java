package Myaong.Gangajikimi.postfound.entity;

import Myaong.Gangajikimi.common.BaseEntity;
import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogType;
import Myaong.Gangajikimi.member.entity.Member;
import jakarta.persistence.Entity;

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
public class PostFound extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 사용자 아이디

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DogType dogType; // 견종

    @Column(nullable = false)
    private String dogColor;

    @Column
    @Enumerated(EnumType.STRING)
    private DogGender dogGender;

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
}