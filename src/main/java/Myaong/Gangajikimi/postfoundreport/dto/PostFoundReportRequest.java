package Myaong.Gangajikimi.postfoundreport.dto;

import Myaong.Gangajikimi.common.enums.ReportType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostFoundReportRequest {

    @NotNull(message = "신고 유형은 필수입니다")
    private ReportType reportType;

    @Size(max = 500, message = "신고 내용은 500자 이하로 입력해주세요")
    private String reportContent;
}
