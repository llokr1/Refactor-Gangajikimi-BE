package Myaong.Gangajikimi.member.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final
    MemberRepository memberRepository;

    public Member findMemberById(Long memberId){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));

        return member;
    }

    public Member findMemberByEmail(String email){

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));

        return member;
    }

    public Member saveMember(Member member){

        return memberRepository.save(member);

    }
}