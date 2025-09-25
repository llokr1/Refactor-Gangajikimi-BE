package Myaong.Gangajikimi.postfoundreport.service;

import Myaong.Gangajikimi.common.exception.GeneralException;
import Myaong.Gangajikimi.common.response.ErrorCode;
import Myaong.Gangajikimi.member.entity.Member;
import Myaong.Gangajikimi.member.repository.MemberRepository;
import Myaong.Gangajikimi.postfound.entity.PostFound;
import Myaong.Gangajikimi.postfound.repository.PostFoundRepository;
import Myaong.Gangajikimi.postfoundreport.entity.PostFoundReport;
import Myaong.Gangajikimi.postfoundreport.repository.PostFoundReportRepository;
import Myaong.Gangajikimi.postfoundreport.dto.PostFoundReportRequest;
import Myaong.Gangajikimi.postfoundreport.dto.PostFoundReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostFoundReportService {

    private final PostFoundReportRepository postFoundReportRepository;
    private final PostFoundRepository postFoundRepository;
    private final MemberRepository memberRepository;

    public PostFoundReportResponse reportPostFound(Long postFoundId, PostFoundReportRequest request, Long reporterId) {
        // 게시글 존재 확인
        PostFound postFound = postFoundRepository.findById(postFoundId)
                .orElseThrow(() -> new GeneralException(ErrorCode.POST_NOT_FOUND));

        // 신고자 존재 확인
        Member reporter = memberRepository.findById(reporterId)
                .orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));

        // 본인 게시글 신고 방지
        if (postFound.getMember().equals(reporter)) {
            throw new GeneralException(ErrorCode.CANNOT_REPORT_OWN_POST);
        }

        // 중복 신고 방지
        if (postFoundReportRepository.existsByPostFoundIdAndReporterId(postFoundId, reporterId)) {
            throw new GeneralException(ErrorCode.ALREADY_REPORTED);
        }

        // 신고 생성
        PostFoundReport report = PostFoundReport.of(postFound, reporter, request.getReportType(), request.getReportContent());
        PostFoundReport savedReport = postFoundReportRepository.save(report);

        return PostFoundReportResponse.of(postFoundId, savedReport.getCreatedAt());
    }
}
