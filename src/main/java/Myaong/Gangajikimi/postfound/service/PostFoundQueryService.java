package Myaong.Gangajikimi.postfound.service;

import Myaong.Gangajikimi.common.dto.PageResponse;
import Myaong.Gangajikimi.postfound.web.dto.response.PostFoundHomeResponse;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.common.util.TimeUtil;
import Myaong.Gangajikimi.postfound.entity.PostFound;
import Myaong.Gangajikimi.postfound.repository.PostFoundRepository;
import Myaong.Gangajikimi.postfound.web.dto.response.PostFoundDetailResponse;
import Myaong.Gangajikimi.s3file.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostFoundQueryService {

    private final PostFoundRepository postFoundRepository;
    private final S3Service s3Service;

    public PostFound findPostFoundById(Long postId) {

        PostFound postFound = postFoundRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));

        return postFound;

    }

    public PostFoundDetailResponse getPostFoundDetail(Long postId) {
        PostFound postFound = postFoundRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));

        // 이미지 Presigned URL 생성 (실제 이미지만)
        List<String> realImageUrls = null;
        if (postFound.getRealImage() != null && !postFound.getRealImage().isEmpty()) {
            realImageUrls = s3Service.generatePresignedUrls(postFound.getRealImage());
        }

        // TODO: AI 이미지 생성 로직 구현 후 활성화
        // String aiImageUrl = null;
        // if (postFound.getAiImage() != null && !postFound.getAiImage().isEmpty()) {
        //     aiImageUrl = s3Service.generatePresignedUrl(postFound.getAiImage());
        // }

        return PostFoundDetailResponse.of(
                postFound.getId(),
                postFound.getTitle(),
                postFound.getDogType(),
                postFound.getDogColor(),
                postFound.getDogGender(),
                postFound.getContent(),
                postFound.getFoundDate(),
                postFound.getFoundTime(),
                postFound.getFoundSpot().getX(), // longitude
                postFound.getFoundSpot().getY(), // latitude
                // TODO: 주소 변환 API 연동 후 활성화 (예: "서울시 송파구")
                // getAddressFromCoordinates(postFound.getFoundSpot().getX(), postFound.getFoundSpot().getY()),
                // TODO: AI 이미지 생성 로직 구현 후 활성화
                // aiImageUrl,
                realImageUrls,
                postFound.getMember().getId(), // authorId
                postFound.getMember().getMemberName(),
                postFound.getCreatedAt(),
                TimeUtil.getTimeAgo(postFound.getCreatedAt())
        );
    }

    /**
     * 발견했어요 게시글 목록 조회 (메인 페이지용)
     */
    public PageResponse getFoundPosts(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PostFound> foundPosts = postFoundRepository.findAllByOrderByCreatedAtDesc(pageable);
        
        // TODO: 필터링 기능 구현 예정
        
        // PostFound를 PostFoundHomeResponse로 변환
        List<PostFoundHomeResponse> foundResponses = foundPosts.getContent().stream()
            .map(PostFoundHomeResponse::from)
            .toList();
        
        // hasNext 계산: Spring Data JPA Page 객체의 hasNext() 메서드 사용
        boolean hasNext = foundPosts.hasNext();
        
        return PageResponse.of(foundResponses, hasNext);
    }

    /**
     * 마이페이지용 내 발견했어요 게시글 조회
     */
    public PageResponse getMyFoundPosts(Long memberId, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PostFound> foundPosts = postFoundRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageable);
        
        // PostFound를 PostFoundHomeResponse로 변환
        var foundResponses = foundPosts.getContent().stream()
            .map(PostFoundHomeResponse::from)
            .toList();
        
        // hasNext 계산: Spring Data JPA Page 객체의 hasNext() 메서드 사용
        boolean hasNext = foundPosts.hasNext();
        
        return PageResponse.of(foundResponses, hasNext);
    }

}
