package Myaong.Gangajikimi.templocation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTempLocation is a Querydsl query type for TempLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTempLocation extends EntityPathBase<TempLocation> {

    private static final long serialVersionUID = -1381390927L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTempLocation tempLocation = new QTempLocation("tempLocation");

    public final Myaong.Gangajikimi.common.QBaseEntity _super = new Myaong.Gangajikimi.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final Myaong.Gangajikimi.postfound.entity.QPostFound postFound;

    public final ComparablePath<org.locationtech.jts.geom.Point> spot = createComparable("spot", org.locationtech.jts.geom.Point.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTempLocation(String variable) {
        this(TempLocation.class, forVariable(variable), INITS);
    }

    public QTempLocation(Path<? extends TempLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTempLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTempLocation(PathMetadata metadata, PathInits inits) {
        this(TempLocation.class, metadata, inits);
    }

    public QTempLocation(Class<? extends TempLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postFound = inits.isInitialized("postFound") ? new Myaong.Gangajikimi.postfound.entity.QPostFound(forProperty("postFound"), inits.get("postFound")) : null;
    }

}

