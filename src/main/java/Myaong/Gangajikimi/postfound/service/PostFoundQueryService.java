package Myaong.Gangajikimi.postfound.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.common.util.TimeUtil;
import Myaong.Gangajikimi.postfound.entity.PostFound;
import Myaong.Gangajikimi.postfound.repository.PostFoundRepository;
import Myaong.Gangajikimi.postfound.web.dto.response.PostFoundDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFoundQueryService {

    private final PostFoundRepository postFoundRepository;

    public PostFound findPostFoundById(Long postId) {

        PostFound postFound = postFoundRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));

        return postFound;

    }

    public PostFoundDetailResponse getPostFoundDetail(Long postId) {
        PostFound postFound = postFoundRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));

        // TODO: 실제 주소 변환 로직 구현 (현재는 좌표만 반환)
        String foundLocation = String.format("위도: %f, 경도: %f", 
                postFound.getFoundSpot().getY(), postFound.getFoundSpot().getX());

        return PostFoundDetailResponse.of(
                postFound.getId(),
                postFound.getTitle(),
                null, // PostFound에는 dogName이 없음
                postFound.getDogType(),
                postFound.getDogColor(),
                postFound.getDogGender(),
                postFound.getContent(),
                postFound.getFoundDate(),
                postFound.getFoundTime(),
                foundLocation,
                postFound.getAiImage(),
                postFound.getRealImage(),
                postFound.getMember().getMemberName(),
                postFound.getCreatedAt(),
                TimeUtil.getTimeAgo(postFound.getCreatedAt())
        );
    }

}
