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
    private final S3Service s3Service;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public PostLost postPostLost(PostLostRequest request, Member member){

        DogType dogType = DogType.valueOf(request.getDogType());
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

        // 이미지 업로드 및 keyName 목록 생성
        List<String> imageKeyNames = new ArrayList<>();
        if (request.getDogImages() != null && !request.getDogImages().isEmpty()) {
            for (MultipartFile image : request.getDogImages()) {
                if (image != null && !image.isEmpty()) {
                    String keyName = s3Service.upload(image, "postLost", savedPostLost.getId().toString());
                    imageKeyNames.add(keyName);
                }
            }
        }

        // 업로드된 이미지 keyNames로 PostLost 업데이트
        savedPostLost.updateImages(imageKeyNames);

        return postLostRepository.save(savedPostLost);
    }

    public PostLost updatePostLost(PostLostRequest request, Member member, PostLost postLost){

        // 권한 확인
        if(!member.equals(postLost.getMember())){
            throw new GeneralException(ErrorCode.UNAUTHORIZED_UPDATING);
        }

        Point point = geometryFactory.createPoint(new Coordinate(request.getLostLongitude(), request.getLostLatitude()));

        // 기존 이미지 삭제 (S3에서)
        if (postLost.getRealImage() != null && !postLost.getRealImage().isEmpty()) {
            for (String oldKeyName : postLost.getRealImage()) {
                s3Service.deleteFile(oldKeyName);
            }
        }

        // 새 이미지 업로드
        List<String> newImageKeyNames = new ArrayList<>();
        if (request.getDogImages() != null && !request.getDogImages().isEmpty()) {
            for (MultipartFile image : request.getDogImages()) {
                if (image != null && !image.isEmpty()) {
                    String keyName = s3Service.upload(image, "postLost", postLost.getId().toString());
                    newImageKeyNames.add(keyName);
                }
            }
        }

        // 게시글 정보 업데이트 (이미지 제외)
        postLost.update(request, point);
        
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


}
