package Myaong.Gangajikimi.postlost.service;

import Myaong.Gangajikimi.common.dto.PageResponse;
import Myaong.Gangajikimi.postlost.web.dto.response.PostLostHomeResponse;
import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.common.util.TimeUtil;
import Myaong.Gangajikimi.postlost.entity.PostLost;
import Myaong.Gangajikimi.postlost.repository.PostLostRepository;
import Myaong.Gangajikimi.postlost.web.dto.response.PostLostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLostQueryService {

    private final PostLostRepository postLostRepository;

    public PostLost findPostLostById(Long postId) {

        PostLost postLost = postLostRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));

        return postLost;

    }

    public PostLostDetailResponse getPostLostDetail(Long postId) {
        PostLost postLost = postLostRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));

        return PostLostDetailResponse.of(
                postLost.getId(),
                postLost.getTitle(),
                postLost.getDogName(),
                postLost.getDogType(),
                postLost.getDogColor(),
                postLost.getDogGender(),
                postLost.getContent(),
                postLost.getLostDate(),
                postLost.getLostTime(),
                postLost.getLostSpot().getX(), // longitude
                postLost.getLostSpot().getY(), // latitude
                // TODO: 주소 변환 API 연동 후 활성화 (예: "서울시 송파구")
                // getAddressFromCoordinates(postLost.getLostSpot().getX(), postLost.getLostSpot().getY()),
                // TODO: Cloud 스토리지 연동 후 활성화
                // postLost.getAiImage(),
                // postLost.getRealImage(),
                postLost.getMember().getId(), // authorId
                postLost.getMember().getMemberName(),
                postLost.getCreatedAt(),
                TimeUtil.getTimeAgo(postLost.getCreatedAt())
        );
    }

    /**
     * 잃어버렸어요 게시글 목록 조회 (메인 페이지용)
     */
    public PageResponse getLostPosts(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PostLost> lostPosts = postLostRepository.findAllByOrderByCreatedAtDesc(pageable);
        
        // TODO: 필터링 기능 구현 예정
        
        List<PostLostHomeResponse> lostResponses = lostPosts.getContent().stream()
        // PostLost를 PostLostHomeResponse로 변환
            .map(PostLostHomeResponse::from)
            .toList();
        
        // hasNext 계산: Spring Data JPA Page 객체의 hasNext() 메서드 사용
        boolean hasNext = lostPosts.hasNext();
        
        return PageResponse.of(lostResponses, hasNext);
    }

    /**
     * 마이페이지용 내 잃어버렸어요 게시글 조회
     */
    public PageResponse getMyLostPosts(Long memberId, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PostLost> lostPosts = postLostRepository.findByMemberIdOrderByCreatedAtDesc(memberId, pageable);
        
        // PostLost를 PostLostHomeResponse로 변환
        var lostResponses = lostPosts.getContent().stream()
            .map(PostLostHomeResponse::from)
            .toList();
        
        // hasNext 계산: Spring Data JPA Page 객체의 hasNext() 메서드 사용
        boolean hasNext = lostPosts.hasNext();
        
        return PageResponse.of(lostResponses, hasNext);
    }

}
