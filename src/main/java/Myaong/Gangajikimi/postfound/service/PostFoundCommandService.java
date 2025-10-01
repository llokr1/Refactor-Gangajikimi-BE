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
public class PostFoundCommandService {

    private final PostFoundRepository postFoundRepository;
    private final S3Service s3Service;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public PostFound postPostFound(PostFoundRequest request, Member member){

        DogType dogType = DogType.valueOf(request.getDogType());
        DogGender dogGender = DogGender.valueOf(request.getDogGender());

        Point newPoint = geometryFactory.createPoint(new Coordinate(request.getFoundLongitude(), request.getFoundLatitude()));

        // 먼저 PostFound를 저장해서 ID를 얻음
        PostFound newPostFound = PostFound.of(null, // 이미지는 나중에 설정
                member,
                request.getTitle(),
                dogType,
                dogGender,
                request.getDogColor(),
                request.getFeatures(),
                newPoint,
                request.getFoundDate(),
                request.getFoundTime());

        PostFound savedPostFound = postFoundRepository.save(newPostFound);

        // 이미지 업로드 및 keyName 목록 생성
        List<String> imageKeyNames = new ArrayList<>();
        if (request.getDogImages() != null && !request.getDogImages().isEmpty()) {
            for (MultipartFile image : request.getDogImages()) {
                if (image != null && !image.isEmpty()) {
                    String keyName = s3Service.upload(image, "postFound", savedPostFound.getId().toString());
                    imageKeyNames.add(keyName);
                }
            }
        }

        // 업로드된 이미지 keyNames로 PostFound 업데이트
        savedPostFound.updateImages(imageKeyNames);

        return postFoundRepository.save(savedPostFound);
    }

    public PostFound updatePostFound(PostFoundRequest request, Member member, PostFound postFound){

        // 권한 확인
        if(!member.equals(postFound.getMember())){
            throw new GeneralException(ErrorCode.UNAUTHORIZED_UPDATING);
        }

        Point point = geometryFactory.createPoint(new Coordinate(request.getFoundLongitude(), request.getFoundLatitude()));

        // 기존 이미지 삭제 (S3에서)
        if (postFound.getRealImage() != null && !postFound.getRealImage().isEmpty()) {
            for (String oldKeyName : postFound.getRealImage()) {
                s3Service.deleteFile(oldKeyName);
            }
        }

        // 새 이미지 업로드
        List<String> newImageKeyNames = new ArrayList<>();
        if (request.getDogImages() != null && !request.getDogImages().isEmpty()) {
            for (MultipartFile image : request.getDogImages()) {
                if (image != null && !image.isEmpty()) {
                    String keyName = s3Service.upload(image, "postFound", postFound.getId().toString());
                    newImageKeyNames.add(keyName);
                }
            }
        }

        // 게시글 정보 업데이트 (이미지 제외)
        postFound.update(request, point);
        
        // 새 이미지 keyName으로 업데이트
        postFound.updateImages(newImageKeyNames);

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

