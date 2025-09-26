package Myaong.Gangajikimi.fixedlocation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFixedLocation is a Querydsl query type for FixedLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFixedLocation extends EntityPathBase<FixedLocation> {

    private static final long serialVersionUID = -1882906331L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFixedLocation fixedLocation = new QFixedLocation("fixedLocation");

    public final Myaong.Gangajikimi.common.QBaseEntity _super = new Myaong.Gangajikimi.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final Myaong.Gangajikimi.postlost.entity.QPostLost postLost;

    public final ComparablePath<org.locationtech.jts.geom.Point> spot = createComparable("spot", org.locationtech.jts.geom.Point.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QFixedLocation(String variable) {
        this(FixedLocation.class, forVariable(variable), INITS);
    }

    public QFixedLocation(Path<? extends FixedLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFixedLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFixedLocation(PathMetadata metadata, PathInits inits) {
        this(FixedLocation.class, metadata, inits);
    }

    public QFixedLocation(Class<? extends FixedLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postLost = inits.isInitialized("postLost") ? new Myaong.Gangajikimi.postlost.entity.QPostLost(forProperty("postLost"), inits.get("postLost")) : null;
    }

}

