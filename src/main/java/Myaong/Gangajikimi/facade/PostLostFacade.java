package Myaong.Gangajikimi.facade;

import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.service.MemberService;
import Myaong.Gangajikimi.postlost.entity.PostLost;
import Myaong.Gangajikimi.postlost.service.PostLostCommandService;
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

    @Transactional
    public PostLostPostResponse postPostLost(PostLostRequest request, Long memberId){

        // Member 생성
        Member member = memberService.findMemberById(memberId);

        // TODO: 생성된 AI 이미지 추가

        // 게시글 생성
        PostLost postLost = postLostCommandService.postPostLost(request, member);

        // DB 저장

        return PostLostPostResponse.of(postLost.getId(), member.getMemberName(), postLost.getTitle());
    }


}
