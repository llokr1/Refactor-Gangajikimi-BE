package Myaong.Gangajikimi.facade;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.service.MemberService;
import Myaong.Gangajikimi.postlost.entity.PostLost;
import Myaong.Gangajikimi.postlost.service.PostLostCommandService;
import Myaong.Gangajikimi.postlost.service.PostLostQueryService;
import Myaong.Gangajikimi.postlost.web.dto.request.PostLostRequest;
import Myaong.Gangajikimi.postlost.web.dto.response.PostLostPostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLostFacade {

    private final MemberService memberService;
    private final PostLostCommandService postLostCommandService;
    private final PostLostQueryService postLostQueryService;

    @Transactional
    public PostLostPostResponse postPostLost(PostLostRequest request, Long memberId){

        // Member 생성
        Member member = memberService.findMemberById(memberId);

        // TODO: 생성된 AI 이미지 추가

        // 게시글 생성
        PostLost postLost = postLostCommandService.postPostLost(request, member);

        // DB 저장

        return PostLostPostResponse.of(postLost.getId(), member.getMemberName(), postLost.getTitle(), postLost.getCreatedAt());
    }

    @Transactional
    public PostLostPostResponse updatePostLost(PostLostRequest request, Long memberId, Long postLostId){

        // Member 조회
        Member member = memberService.findMemberById(memberId);

        // 게시글 조회
        PostLost postLost = postLostQueryService.findPostLostById(postLostId);

        //업데이트 후 결과 반환
        return PostLostPostResponse.from(postLostCommandService.updatePostLost(request, member, postLost));
    }

    @Transactional
    public void deletePostLost(Long memberId, Long postLostId){

        Member member = memberService.findMemberById(memberId);

        PostLost postLost = postLostQueryService.findPostLostById(postLostId);

        postLostCommandService.deletePostLost(postLost, member);
    }

}
