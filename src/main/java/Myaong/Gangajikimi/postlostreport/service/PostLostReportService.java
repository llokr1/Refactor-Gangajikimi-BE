package Myaong.Gangajikimi.postlostreport.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.repository.MemberRepository;
import Myaong.Gangajikimi.postlost.entity.PostLost;
import Myaong.Gangajikimi.postlost.repository.PostLostRepository;
import Myaong.Gangajikimi.postlostreport.entity.PostLostReport;
import Myaong.Gangajikimi.postlostreport.repository.PostLostReportRepository;
import Myaong.Gangajikimi.postlostreport.dto.PostLostReportRequest;
import Myaong.Gangajikimi.postlostreport.dto.PostLostReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLostReportService {

    private final PostLostReportRepository postLostReportRepository;
    private final PostLostRepository postLostRepository;
    private final MemberRepository memberRepository;

    public PostLostReportResponse reportPostLost(Long postLostId, PostLostReportRequest request, Long reporterId) {
        // 게시글 존재 확인
        PostLost postLost = postLostRepository.findById(postLostId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));

        // 신고자 존재 확인
        Member reporter = memberRepository.findById(reporterId)
                .orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));

        // 본인 게시글 신고 방지
        if (postLost.getMember().equals(reporter)) {
            throw new GeneralException(ErrorCode.CANNOT_REPORT_OWN_POST);
        }

        // 중복 신고 방지
        if (postLostReportRepository.existsByPostLostIdAndReporterId(postLostId, reporterId)) {
            throw new GeneralException(ErrorCode.ALREADY_REPORTED);
        }

        // 신고 생성
        PostLostReport report = PostLostReport.of(postLost, reporter, request.getReportType(), request.getReportContent());
        PostLostReport savedReport = postLostReportRepository.save(report);

        return PostLostReportResponse.of(postLostId, savedReport.getCreatedAt());
    }
}
