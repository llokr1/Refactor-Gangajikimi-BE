package Myaong.Gangajikimi.postfound.service;

import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogType;
import Myaong.Gangajikimi.common.enums.Role;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.postfound.entity.PostFound;
import Myaong.Gangajikimi.postfound.repository.PostFoundRepository;
import Myaong.Gangajikimi.postfound.web.dto.request.PostFoundRequest;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFoundCommandService {

    private final PostFoundRepository postFoundRepository;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public PostFound postPostFound(PostFoundRequest request, Member member){

        DogType dogType = DogType.valueOf(request.getDogType());
        DogGender dogGender = DogGender.valueOf(request.getDogGender());

        Point newPoint = geometryFactory.createPoint(new Coordinate(request.getFoundLongitude(), request.getFoundLatitude()));

        PostFound newPostFound = PostFound.of(request.getDogImages(),
                member,
                request.getTitle(),
                dogType,
                dogGender,
                request.getDogColor(),
                request.getFeatures(),
                newPoint,
                request.getFoundDate(),
                request.getFoundTime());


        return postFoundRepository.save(newPostFound);
    }

    public PostFound updatePostFound(PostFoundRequest request, Member member, PostFound postFound){

        // 권환 확인
        if(!member.equals(postFound.getMember())){
            throw new GeneralException(ErrorCode.UNAUTHORIZED_UPDATING);
        }

        Point point = geometryFactory.createPoint(new Coordinate(request.getFoundLongitude(), request.getFoundLatitude()));

        postFound.update(request, point);

        return postFound;
    }

    public void deletePostFound(PostFound postFound, Member member){

        boolean isOwner = member.equals(postFound.getMember());

        boolean isAdmin = member.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new GeneralException(ErrorCode.UNAUTHORIZED_DELETING);
        }

        if(!postFoundRepository.existsById(postFound.getId())){
            throw new GeneralException(ErrorCode.POST_NOT_FOUND);
        }
        postFoundRepository.delete(postFound);
    }

}