package Myaong.Gangajikimi.postlost.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.common.util.TimeUtil;
import Myaong.Gangajikimi.postlost.entity.PostLost;
import Myaong.Gangajikimi.postlost.repository.PostLostRepository;
import Myaong.Gangajikimi.postlost.web.dto.response.PostLostDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

        // TODO: 실제 주소 변환 로직 구현 (현재는 좌표만 반환)
        String lostLocation = String.format("위도: %f, 경도: %f", 
                postLost.getLostSpot().getY(), postLost.getLostSpot().getX());

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
                lostLocation,
                postLost.getAiImage(),
                postLost.getRealImage(),
                postLost.getMember().getMemberName(),
                postLost.getCreatedAt(),
                TimeUtil.getTimeAgo(postLost.getCreatedAt())
        );
    }

}
