package Myaong.Gangajikimi.templocation.entity;

import Myaong.Gangajikimi.common.BaseEntity;
import Myaong.Gangajikimi.postfound.entity.PostFound;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempLocation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostFound postFound;

    @Column(nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point spot;
}
