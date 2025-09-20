package Myaong.Gangajikimi.postlost.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.postlost.entity.PostLost;
import Myaong.Gangajikimi.postlost.repository.PostLostRepository;
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

}
