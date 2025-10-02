package Myaong.Gangajikimi.postfound.service;

import Myaong.Gangajikimi.common.enums.DogGender;
import Myaong.Gangajikimi.common.enums.DogStatus;
import Myaong.Gangajikimi.dogtype.entity.DogType;
import Myaong.Gangajikimi.dogtype.service.DogTypeService;
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
    private final DogTypeService dogTypeService;
    private final S3Service s3Service;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public PostFound postPostFound(PostFoundRequest request, Member member, List<MultipartFile> images){

        DogType dogType = dogTypeService.findByTypeName(request.getDogType());
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
        if (images != null && !images.isEmpty()) {
            imageKeyNames = images.stream()
                    .filter(image -> image != null && !image.isEmpty())
                    .map(image -> s3Service.upload(image, "postFound", savedPostFound.getId().toString()))
                    .toList();
        }

        // 업로드된 이미지 keyNames로 PostFound 업데이트
        savedPostFound.updateImages(imageKeyNames);

        return savedPostFound;
    }

    public PostFound updatePostFound(PostFoundRequest request, Member member, PostFound postFound, List<MultipartFile> images){

        // 권한 확인
        if(!member.equals(postFound.getMember())){
            throw new GeneralException(ErrorCode.UNAUTHORIZED_UPDATING);
        }

        Point point = geometryFactory.createPoint(new Coordinate(request.getFoundLongitude(), request.getFoundLatitude()));

        // 기존 이미지 삭제 (S3에서)
        if (postFound.getRealImage() != null && !postFound.getRealImage().isEmpty()) {
            postFound.getRealImage().forEach(s3Service::deleteFile);
        }

        // 새 이미지 업로드
        List<String> newImageKeyNames = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            newImageKeyNames = images.stream()
                    .filter(image -> image != null && !image.isEmpty())
                    .map(image -> s3Service.upload(image, "postFound", postFound.getId().toString()))
                    .toList();
        }

        // 게시글 정보 업데이트 (이미지 제외)
        DogType dogType = dogTypeService.findByTypeName(request.getDogType());
        postFound.update(request, point, dogType);
        
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

    /**
     * PostFound의 DogStatus만 업데이트
     */
    public PostFound updatePostFoundStatus(PostFound postFound, Member member, DogStatus dogStatus) {
        
        // TODO: 권한 확인 - 본인만 상태 변경 가능
        // if (!member.equals(postFound.getMember())) {
        //     throw new GeneralException(ErrorCode.UNAUTHORIZED_UPDATING);
        // }

        // 상태 업데이트
        postFound.updateStatus(dogStatus);
        
        return postFound;
    }

}

