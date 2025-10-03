package Myaong.Gangajikimi.postlost.service;

import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogStatus;
import Myaong.Gangajikimi.dogtype.entity.DogType;
import Myaong.Gangajikimi.dogtype.service.DogTypeService;
import Myaong.Gangajikimi.common.enums.Role;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.postlost.entity.PostLost;
import Myaong.Gangajikimi.postlost.repository.PostLostRepository;
import Myaong.Gangajikimi.postlost.web.dto.request.PostLostRequest;
import Myaong.Gangajikimi.s3file.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLostCommandService {

    private final PostLostRepository postLostRepository;
    private final DogTypeService dogTypeService;
    private final S3Service s3Service;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public PostLost postPostLost(PostLostRequest request, Member member, List<MultipartFile> images){

        DogType dogType = dogTypeService.findByTypeName(request.getDogType());
        DogGender dogGender = DogGender.valueOf(request.getDogGender());

        Point newPoint = geometryFactory.createPoint(new Coordinate(request.getLostLongitude(), request.getLostLatitude()));

        // 먼저 PostLost를 저장해서 ID를 얻음
        PostLost newPostLost = PostLost.of(null, // 이미지는 나중에 설정
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

        PostLost savedPostLost = postLostRepository.save(newPostLost);

        // 이미지 업로드 및 keyName 목록 생성 (stream 사용)
        List<String> imageKeyNames = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            imageKeyNames = images.stream()
                    .filter(image -> image != null && !image.isEmpty())
                    .map(image -> s3Service.upload(image, "postLost", savedPostLost.getId().toString()))
                    .toList();
        }

        // 업로드된 이미지 keyNames로 PostLost 업데이트
        savedPostLost.updateImages(imageKeyNames);

        return savedPostLost;
    }

    public PostLost updatePostLost(PostLostRequest request, Member member, PostLost postLost, List<MultipartFile> images){

        // 권한 확인
        if(!member.equals(postLost.getMember())){
            throw new GeneralException(ErrorCode.UNAUTHORIZED_UPDATING);
        }

        Point point = geometryFactory.createPoint(new Coordinate(request.getLostLongitude(), request.getLostLatitude()));

        // 기존 이미지 삭제 (S3에서) - stream 사용
        postLost.getRealImage().forEach(s3Service::deleteFile);

        // 새 이미지 업로드 (stream 사용)
        List<String> newImageKeyNames = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            newImageKeyNames = images.stream()
                    .filter(image -> image != null && !image.isEmpty())
                    .map(image -> s3Service.upload(image, "postLost", postLost.getId().toString()))
                    .toList();
        }

        // 게시글 정보 업데이트 (이미지 제외)
        DogType dogType = dogTypeService.findByTypeName(request.getDogType());
        postLost.update(request, point, dogType);
        
        // 새 이미지 keyName으로 업데이트
        postLost.updateImages(newImageKeyNames);

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

    /**
     * PostLost의 DogStatus만 업데이트
     */
    public PostLost updatePostLostStatus(PostLost postLost, Member member, DogStatus dogStatus) {
        
        // TODO: 권한 확인 - 본인만 상태 변경 가능
        // if (!member.equals(postLost.getMember())) {
        //     throw new GeneralException(ErrorCode.UNAUTHORIZED_UPDATING);
        // }

        // 상태 업데이트
        postLost.updateStatus(dogStatus);
        
        return postLost;
    }


}
