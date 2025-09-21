package Myaong.Gangajikimi.postlost.service;

import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogType;
import Myaong.Gangajikimi.common.enums.Role;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.postlost.entity.PostLost;
import Myaong.Gangajikimi.postlost.repository.PostLostRepository;
import Myaong.Gangajikimi.postlost.web.dto.request.PostLostRequest;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLostCommandService {

    private final PostLostRepository postLostRepository;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public PostLost postPostLost(PostLostRequest request, Member member){

        DogType dogType = DogType.valueOf(request.getDogType());
        DogGender dogGender = DogGender.valueOf(request.getDogGender());

        Point newPoint = geometryFactory.createPoint(new Coordinate(request.getLostLongitude(), request.getLostLatitude()));

        PostLost newPostLost = PostLost.of(request.getDogImages(),
                member,
                request.getTitle(),
                request.getDogName(),
                dogType,
                dogGender,
                request.getDogColor(),
                request.getFeatures(),
                newPoint,
                request.getLostDate(),
                request.getLostTime());


        return postLostRepository.save(newPostLost);
    }

    public PostLost updatePostLost(PostLostRequest request, Member member, PostLost postLost){

        // 권환 확인
        if(!member.equals(postLost.getMember())){
            throw new GeneralException(ErrorCode.UNAUTHORIZED_UPDATING);
        }

        Point point = geometryFactory.createPoint(new Coordinate(request.getLostLongitude(), request.getLostLatitude()));

        postLost.update(request, point);

        return postLost;
    }

    public void deletePostLost(PostLost postLost, Member member){

        boolean isOwner = member.equals(postLost.getMember());

        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new GeneralException(ErrorCode.UNAUTHORIZED_DELETING);
        }

        if(!postLostRepository.existsById(postLost.getId())){
            throw new GeneralException(ErrorCode.POST_NOT_FOUND);
        }

        postLostRepository.delete(postLost);
    }


}
